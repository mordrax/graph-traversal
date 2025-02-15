(ns graph-traversal.q3-ai-test
  (:require
   [clojure.test :refer
    [deftest is testing]]
   [graph-traversal.q3-ai :refer
    [shortest-path]]))

(deftest test-simple-shortest-path
  (is (= nil
         (shortest-path {:1 []} :1 :1)))

  (is (= '(:1 :2)
         (shortest-path {:1 [[:2 1]], :2 []} :1 :2))))  