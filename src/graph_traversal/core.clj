(ns graph-traversal.core
  (:gen-class))

(def G {:1 [:2 :3],
        :2 [:4],
        :3 [:4],
        :4 []})

(defn seq-graph [d g s]
  ((fn rec-seq [explored frontier]
     (lazy-seq
      (if (empty? frontier)
        nil
        (let [v (peek frontier)
              neighbors (g v)
              _ (do
                  (println v)
                  (println "neighbors: " neighbors)
                  (println "explored: " explored)
                  (println "frontier: " frontier)
                  (println "next explored: " (into explored neighbors))
                  (println "next frontier: " (into (pop frontier) (remove explored neighbors)))
                  ())]

          (cons v (rec-seq
                   (into explored neighbors)
                   (into (pop frontier) (remove explored neighbors))))))))
   #{s} (conj d s)))

(def seq-graph-dfs (partial seq-graph []))
(def seq-graph-bfs (partial seq-graph (clojure.lang.PersistentQueue/EMPTY)))

(seq-graph-dfs G :1) ; => (:1 :3 :4 :2)
;; (seq-graph-bfs G :1) ; => (:1 :2 :3 :4)
