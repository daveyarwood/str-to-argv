(ns str-to-argv
  (:require [clojure.string :as str]))

; code taken from Michał Marczyk's answer to this stackoverflow question:
; http://stackoverflow.com/questions/3249830/parsing-command-line-arguments-from-a-string-in-clojure

(defn initial-state [input]
  {:expecting nil
   :blocks (mapcat #(str/split % #"(?<=\s)|(?=\s)")
                   (str/split input #"(?<=(?:'|\"|\\))|(?=(?:'|\"|\\))"))
   :arg-blocks []})

(defn arg-parser-step [s]
  (if-let [bs (seq (:blocks s))]
    (if-let [d (:expecting s)]
      (loop [bs bs]
        (cond (= (first bs) d)
              [nil (-> s
                       (assoc-in [:expecting] nil)
                       (update-in [:blocks] next))]
              (= (first bs) "\\")
              [nil (-> s
                       (update-in [:blocks] nnext)
                       (update-in [:arg-blocks]
                                  #(conj (pop %)
                                         (conj (peek %) (second bs)))))]
              :else
              [nil (-> s
                       (update-in [:blocks] next)
                       (update-in [:arg-blocks]
                                  #(conj (pop %) (conj (peek %) (first bs)))))]))
      (cond (#{"\"" "'"} (first bs))
            [nil (-> s
                     (assoc-in [:expecting] (first bs))
                     (update-in [:blocks] next)
                     (update-in [:arg-blocks] conj []))]
            (str/blank? (first bs))
            [nil (-> s (update-in [:blocks] next))]
            :else
            [nil (-> s
                     (update-in [:blocks] next)
                     (update-in [:arg-blocks] conj [(.trim (first bs))]))]))
    [(->> (:arg-blocks s)
          (map (partial apply str)))
     nil]))

(defn split-args [input]
  (loop [s (initial-state input)]
    (let [[result new-s] (arg-parser-step s)]
      (if result result (recur new-s)))))
