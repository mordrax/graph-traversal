(ns graph-traversal.core
  (:gen-class))


(def G {:1 [:2 :3],
        :2 [:4],
        :3 [:4],
        :4 [:5],
        :5 [:6]})

(def WG {:1 [[:2 1] [:3 2]],
         :2 [[:4 4] [:1 4]],
        ;;  :2 [[:4 4] [:3 1] [:1 5]],
         :3 [[:4 2]],
         :4 []})


(defn seq-graph [acc graph init_seed]
  ((fn rec-seq [explored frontier]
     (lazy-seq
      (if (empty? frontier)
        nil
        (let [node (peek frontier) ;wode = weighted node
              neighbors (graph node)]
          (do
            ;; (println "")
            ;; (println "neighbours of " node "- " neighbors)
            ;; (println " explored - " explored)
            ;; (println " neighbors - " neighbors)
            ;; (println " frontier - " frontier)
            ;; (println " next explored" (into explored neighbors))
            ;; (println " pop frontier, remove explored neighbouts- " (pop frontier) (remove explored neighbors))
            ;; (println " next frontier" (into (pop frontier) (remove explored neighbors)))
            (cons node
                  (rec-seq
                   (into explored neighbors)
                   (into
                    ; frontier is what we intended to explore at first, 
                    ; peek frontier exploered the first item
                    ; so now we pop frontier to not explore it again
                    (pop frontier)

                    ; remove explored neighbors from the frontier
                    (remove explored neighbors)))))))))
   #{init_seed} (conj acc init_seed)))

(def seq-graph-dfs (partial seq-graph []))
(def seq-graph-bfs (partial seq-graph (clojure.lang.PersistentQueue/EMPTY)))


(seq-graph-dfs G :1) ; => (:1 :3 :4 :2)
(seq-graph-bfs G :1) ; => (:1 :2 :3 :4)

(seq-graph-dfs WG [:1 0]) ; => (:1 :3 :4 :2)
(seq-graph-bfs WG :1) ; => (:1 :2 :3 :4)