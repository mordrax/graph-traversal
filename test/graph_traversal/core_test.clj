(ns graph-traversal.core-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [graph-traversal.core :as g]))


(deftest test-seq-graph-dfs
  (testing "DFS traversal"
    (let [expected-dfs-result-g [:1 :3 :4 :5 :6 :2] ; Correct DFS order for G
          actual-dfs-result-g (g/seq-graph-dfs g/G :1)

          expected-bfs-result-g [:1 :2 :3 :4 :5 :6] ; Correct BFS order for G
          actual-bfs-result-g (g/seq-graph-bfs g/G :1)

          expected-dfs-result-wg [:1 [:2 1] [:4 4] [:5 nil] [:6 nil] [:3 2]] ; Correct DFS order for WG (adjust as needed)
          actual-dfs-result-wg (g/seq-graph-dfs g/WG [:1 0])]

      (is (= expected-dfs-result-g (vec actual-dfs-result-g)) "DFS on G should produce correct result") ; Use vec to compare
      (is (= expected-bfs-result-g (vec actual-bfs-result-g)) "BFS on G should produce correct result") ; Use vec to compare
      ;; (is (= expected-dfs-result-wg (vec actual-dfs-result-wg)) "DFS on WG should produce correct result")
      ())))
