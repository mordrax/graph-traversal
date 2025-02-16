(ns graph-traversal.q2-test
  (:require
   [clojure.test :refer
    [deftest is testing]]
   [graph-traversal.q2 :refer
    [make-graph
     absolute-edge-from-graph]]))

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

(deftest test-absolute-edge-from-graph
  (testing "generates first edge"
    (is (= [0 1] (absolute-edge-from-graph 5 0))))
  (testing "generates last edge"
    (is (= [4 3] (absolute-edge-from-graph 5 19))))
  (testing "generates invalid edge" ;20 is the first edge of next node [5 0] which does not exist, nodes 0-4
    (is (= [5 0] (absolute-edge-from-graph 5 20))))
  (testing "does not crash on 1 node"
    (is (= nil (absolute-edge-from-graph 1 0))))
  (testing "does not crash on -1 node"
    (is (= nil (absolute-edge-from-graph -1 0))))

  (testing "does not crash on -1 edge"
    (is (= nil (absolute-edge-from-graph 5 -1)))))


;; call make-graph with N=4 and S=3, expect a non-nil output
(deftest test-make-graph-edges
  (testing "generate a graph with 4 nodes and 3 edges"
    (is (not (nil? (make-graph 4 3)))))

  ;; test that a fully connected 5 node, 20 edge graph contains a edge from all nodes
  ;; to all other nodes, eg:
  ;; {:0 ([:4 56] [:3 78] [:2 64] [:1 58]),
  ;;  :4 ([:0 77] [:2 55] [:1 73] [:3 47]),
  ;;  :3 ([:4 85] [:1 86] [:2 69] [:0 0]),
  ;;  :1 ([:2 37] [:0 62] [:4 47] [:3 4]),
  ;;  :2 ([:4 88] [:3 27] [:0 68] [:1 25])}
  ;; (testing "fully connected nodes"
  ;;   (let [graph (make-graph 5 20)]
  ;;     (is (not (nil? graph))
  ;;         (is (every? (fn [[k v]] (every? (fn [[n w]] (contains? (set (map first v)) n)) v)) graph)))))

  (testing "fully connected nodes"
    (let [fully-connected-graph (make-graph 5 20)]

      (is (= 5 (count (make-graph 5 20))))
      (is (= 40 (count (flatten (vals fully-connected-graph))))) ; all edges and weights
      ))

  (testing "edge condition graphs"
    (let [graph (make-graph 5 0)]
      (is (= 0 (count graph)))
      (is (= 0 (count (flatten (vals graph)))))
      (is (= 0 (count (keys graph)))))

    (let [graph (make-graph 1 0)]
      (is (= 0 (count graph)))
      (is (= 0 (count (flatten (vals graph)))))
      (is (= 0 (count (keys graph)))))

    (let [graph (make-graph 5 100)]
      (is (= 0 (count graph)))
      (is (= 0 (count (flatten (vals graph)))))
      (is (= 0 (count (keys graph))))))

  ;; NOTE: Testing fails at 10000 nodes and 100_000 edges, taking too long to run
  ;; 1000 nodes and 100_000 edges takes 3.5s to run
  (testing "big arse graph"
    (let [graph (make-graph 1000 10000)]
      ;; (is (= 1000 (count graph))) ; NOTE: This is not guaranteed because nodes with no directed edge from it are not recorded as []
      (is (= 20000 (count (flatten (vals graph))))))))
