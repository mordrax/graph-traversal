(ns graph-traversal.q4-test
  (:require
   [clojure.test :refer
    [deftest is testing]]
   [graph-traversal.q4 :refer
    [eccentricity radius diameter SIMPLE_GRAPH]]))

(deftest q4-test
  (testing "eccentricity radius diameter of simple graph"
    (is (= 12 (eccentricity SIMPLE_GRAPH :A)))
    (is (= 10 (eccentricity SIMPLE_GRAPH :B)))
    (is (= 12 (eccentricity SIMPLE_GRAPH :C)))
    (is (= 7 (eccentricity SIMPLE_GRAPH :D)))
    (is (= 8 (eccentricity SIMPLE_GRAPH :E)))
    (is (= 9 (eccentricity SIMPLE_GRAPH :F)))
    (is (= 7 (radius SIMPLE_GRAPH)))
    (is (= 12 (diameter SIMPLE_GRAPH)))))

