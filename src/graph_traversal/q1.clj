(ns graph-traversal.q1
  (:gen-class))

(def G {:1 [[:2 1] [:3 2]],
        :2 [[:4 4]],
        :3 [[:4 2]],
        :4 []})

(defn is_explored [explored [node _]]
  (some (fn [x] (= x node)) explored))


(defn seq-graph [acc graph init_seed]
  ((fn rec-seq [explored frontier]
     (lazy-seq
      (if (empty? frontier)
        nil
        (let [[node weight] (peek frontier)
              neighbors (graph node)
              ;; _ (do
              ;;     (println [node weight])
              ;;     (println "neighbors: " neighbors)
              ;;     (println "explored: " explored)
              ;;     (println "frontier: " frontier)
              ;;     (println "next explored: " (into explored neighbors))
              ;;     (println "next frontier: " (into (pop frontier) (remove explored neighbors)))
              ;;     ())
              ]
          (cons [node weight]
                (rec-seq
                 (into explored neighbors)
                 (into (pop frontier) (remove (partial is_explored explored) neighbors))))))))
   #{init_seed} (conj acc init_seed)))

(def seq-graph-dfs (partial seq-graph []))
(def seq-graph-bfs (partial seq-graph clojure.lang.PersistentQueue/EMPTY))

(seq-graph-dfs G [:1 nil]) ; => (:1 :3 :4 :2)
;; (seq-graph-bfs G [:1 nil]) ; => (:1 :2 :3 :4)
