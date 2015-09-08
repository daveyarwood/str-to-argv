(ns str-to-argv.tests
  (:require [clojure.test :refer :all]
            [str-to-argv  :refer (split-args)]))

(deftest str-to-argv-tests
  (testing "splitting args"
    (is (= (split-args "asdf 'asdf \" asdf' \"asdf ' asdf\" asdf")
           '("asdf" "asdf \" asdf" "asdf ' asdf" "asdf")))
    (is (= (split-args "asdf asdf '  asdf \" asdf ' \" foo bar ' baz \" \" foo bar \\\" baz \"")
           '("asdf" "asdf" "  asdf \" asdf " " foo bar ' baz " " foo bar \" baz ")))
    (is (= (split-args "foo --bar 'baz quux'")
           '("foo" "--bar" "baz quux")))))
