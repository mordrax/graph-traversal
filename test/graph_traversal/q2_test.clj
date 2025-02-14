(ns graph-traversal.q2-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [graph-traversal.q2 :refer [make-graph]]))

;; define a 5 node, 20 edge fully connected graph with random weights
(def CONNECTED {:1 [[:2 1] [:3 2] [:4 3] [:5 4]],
                :2 [[:1 5] [:3 6] [:4 7] [:5 8]],
                :3 [[:1 9] [:2 10] [:4 11] [:5 12]],
                :4 [[:1 13] [:2 14] [:3 15] [:5 16]],
                :5 [[:1 17] [:2 18] [:3 19] [:4 20]]})

;; call make-graph with N=4 and S=2, expect a nil output
(deftest test-make-graph
  (testing "S smaller than lower bound"
    (is (nil? (make-graph 4 2))))
  (testing "S larger than upper bound"
    (is (nil? (make-graph 4 13)))) ; 4 * 3 + 1 = 13
  (testing "N is 0"
    (is (nil? (make-graph 0 0)))))

;; call make-graph with N=4 and S=3, expect a non-nil output
(deftest test-make-graph-edges
  (testing "generate a graph with 4 nodes and 3 edges"
    (is (not (nil? (make-graph 4 3)))))

  (testing "generate a fully connected graph of 5 nodes and 20 edges"
    (let [g (make-graph 5 20)]
      (is (not (nil? g)))
      (is (= 5 (count g)))
      (is (= 40 (count (flatten (vals g))))))))