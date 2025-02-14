(ns graph-traversal.q1-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [graph-traversal.q1 :refer [seq-graph-dfs seq-graph-bfs]]))

(def G {:1 [[:2 1] [:3 2]],
        :2 [[:4 4]],
        :3 [[:4 2]],
        :4 []})

(def TINY {:1 []})

(def LOOPY {:1 [[:2 4]],
            :2 [[:1 7]]})

; describe a graph which is a star pattern that starts at :5 and is connected to
; 5 other nodes. Each of the 5 nodes is only connected to one other node, this repeats twice and
; then ends with no more connections (AI prompt)
(def STARRY {:5 [[:1 1] [:2 1] [:3 1] [:4 1] [:16 1]],
             :1 [[:6 1]],
             :2 [[:7 1]],
             :3 [[:8 1]],
             :4 [[:9 1]],
             :16 [[:10 1]],
             :6 [[:11 1]],
             :7 [[:12 1]],
             :8 [[:13 1]],
             :9 [[:14 1]],
             :10 [[:15 1]],
             :11 [],
             :12 [],
             :13 [],
             :14 [],
             :15 []})


(deftest test-seq-graph-dfs
  (testing "simple traversal"
    (let [expected-dfs-result-g [[:1 nil] [:3 2] [:4 2] [:2 1] [:4 4]] ; 1->3, 3->4(2), 1->2(1), 2->4(4)
          actual-dfs-result-g (seq-graph-dfs G :1)

          expected-bfs-result-g [[:1 nil] [:2 1] [:3 2] [:4 4] [:4 2]] ; 1->2, 1->3, 2->4(4), 3->4(2) 
          actual-bfs-result-g (seq-graph-bfs G :1)]

      (is (= expected-dfs-result-g (vec actual-dfs-result-g)) "DFS on G ")
      (is (= expected-bfs-result-g (vec actual-bfs-result-g)) "BFS on G ")
      ()))
  (testing "tiny traversal"
    (let [expected-dfs-result-tiny [[:1 nil]]
          actual-dfs-result-tiny (seq-graph-dfs TINY :1)

          expected-bfs-result-tiny [[:1 nil]]
          actual-bfs-result-tiny (seq-graph-bfs TINY :1)]

      (is (= expected-dfs-result-tiny (vec actual-dfs-result-tiny)) "DFS on TINY ")
      (is (= expected-bfs-result-tiny (vec actual-bfs-result-tiny)) "BFS on TINY ")
      ()))
  (testing "loopy traversal"
    (let [expected-dfs-result-loopy [[:1 nil] [:2 4] [:1 7]]
          actual-dfs-result-loopy (take 3 (seq-graph-dfs LOOPY :1))

          expected-bfs-result-loopy [[:1 nil] [:2 4] [:1 7] [:2 4] [:1 7] [:2 4]]
          actual-bfs-result-loopy (take 6 (seq-graph-bfs LOOPY :1))]

      (is (= expected-dfs-result-loopy (vec actual-dfs-result-loopy)) "DFS on LOOPY ")
      (is (= expected-bfs-result-loopy (vec actual-bfs-result-loopy)) "BFS on LOOPY ")
      ()))
  (testing "starry dfs from 4 is the same as bfs from 4"
    (let [actual-dfs-result-starry (seq-graph-dfs STARRY :4)
          actual-bfs-result-starry (seq-graph-bfs STARRY :4)]

      (is (= (vec actual-bfs-result-starry) (vec actual-dfs-result-starry)) "Stars align")
      ())
    ()))
