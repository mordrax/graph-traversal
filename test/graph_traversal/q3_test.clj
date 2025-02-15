(ns graph-traversal.q3-test
  (:require
   [clojure.test :refer
    [deftest is testing]]
   [graph-traversal.q3 :refer
    [shortest-path SIMPLE_GRAPH]]))

(deftest test-simple-shortest-path
  (is (= nil
         (shortest-path {:1 []} :1 :1)))

  (is (= '(:1 :2)
         (shortest-path {:1 [[:2 1]], :2 []} :1 :2)))


  (is (= '(:1 :3 :2 :4))
      (shortest-path SIMPLE_GRAPH :1 :5)))
  