(ns graph-traversal.q3-test
  (:require
   [clojure.test :refer
    [deftest is testing]]
   [graph-traversal.q3 :refer
    [shortest-path SIMPLE_GRAPH]]))

(def UNCONNECTED_GRAPH
  {:0 [[:9 13] [:8 81] [:7 37] [:6 15] [:5 61] [:4 6] [:3 66] [:2 75] [:1 26]], :8 [[:5 1]]})


(deftest test-simple-shortest-path
  (is (= [:1]
         (shortest-path {:1 []} :1 :1)))

  (is (= '(:1 :2)
         (shortest-path {:1 [[:2 1]], :2 []} :1 :2)))


  (is (= '(:A :B :D :F :C)
         (shortest-path SIMPLE_GRAPH :A :C)))

  (testing "Don't crash on unconnected graph"
    (is (= [:0 :4]
           (shortest-path UNCONNECTED_GRAPH :0 :4))))

  (testing "simple graph"))
  