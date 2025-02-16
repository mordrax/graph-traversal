(ns graph-traversal.q3-test
  (:require
   [clojure.test :refer
    [deftest is testing]]
   [graph-traversal.q3 :refer
    [shortest-path SIMPLE_GRAPH GRAPH_15_100]]))

(def UNCONNECTED_GRAPH
  {:0 [[:9 13] [:8 81] [:7 37] [:6 15] [:5 61] [:4 6] [:3 66] [:2 75] [:1 26]], :8 [[:5 1]]})


(deftest test-simple-shortest-path
  (is (= [:1]
         (shortest-path {:1 []} :1 :1)))

  (is (= '(:1 :2)
         (shortest-path {:1 [[:2 1]], :2 []} :1 :2)))

  (testing "simple graph"
    (is (= '(:A :B :D :F :C)
           (shortest-path SIMPLE_GRAPH :A :C))))

  (testing "Don't crash on unconnected graph"
    (is (= [:0 :4]
           (shortest-path UNCONNECTED_GRAPH :0 :4))))

  (testing "15 node, 100 edge graph"
    ; {:14 {:distance 12, :path [:12 :14]},
;  :12 {:distance 0, :path [:12]},
;  :11 {:distance 63, :path [:12 :14 :13 :11]},
;  :10 {:distance 40, :path [:12 :6 :1 :10]},
;  :13 {:distance 12, :path [:12 :14 :13]},
;  :0 {:distance 55, :path [:12 :14 :7 :0]},
;  :4 {:distance 63, :path [:12 :14 :13 :11 :4]},
;  :7 {:distance 23, :path [:12 :14 :7]},
;  :1 {:distance 21, :path [:12 :6 :1]},
;  :8 {:distance 16, :path [:12 :14 :13 :8]},
;  :9 {:distance 26, :path [:12 :14 :13 :9]},
;  :2 {:distance 65, :path [:12 :6 :2]},
;  :5 {:distance 81, :path [:12 :5]},
;  :3 {:distance 45, :path [:12 :14 :13 :9 :3]},
;  :6 {:distance 13, :path [:12 :6]}}
;; [:12 :6 :2]
    (is (= [:12 :14 :13]
           (shortest-path GRAPH_15_100 :12 :13)))))
  