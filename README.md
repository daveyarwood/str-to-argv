# str-to-argv

## What? Why?

This library serves the very niche purpose of [parsing command-line arguments from a single string in Clojure](http://stackoverflow.com/questions/3249830/parsing-command-line-arguments-from-a-string-in-clojure). Usually, your shell takes care of splitting command-line arguments, so this is something you would never need to do manually. Once in a blue moon, though, you may need to do this. So, here's some code that does it.

The implementation is copy-pasted from [Michał Marczyk's answer](http://stackoverflow.com/a/3252361/2338327) to the StackOverflow question linked above. After lots of googling, it was the best solution I could find. I was hesitant to include it in the project where I needed it, so I made a reusable library out of it.

> Note: I'm totally prepared to scrap this and replace it with something less convoluted (Michał's word, not mine!), if something comes up.

> This solution is also from 2010, at which point there apparently (from reading the comments) wasn't a proper parser-generator library available. Perhaps we could now build a simpler solution using [Instaparse](https://github.com/Engelberg/instaparse).

## Usage

```clojure
(require '[str-to-argv :refer (split-args)])

(split-args "foo --bar 'baz quux'")
;=> ("foo" "--bar" "baz quux")
```

