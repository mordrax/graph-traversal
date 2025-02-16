;; 3. Write an implementation of Dijkstra's algorithm that traverses your graph and outputs the shortest path between any 2 randomly selected vertices.
;; I should be able to write something like this for example.

;; #!clojure
;; (def random-graph (make-graph 10 10))
;; (shortest-path 
;;    random-graph 
;;    (first (keys random-graph)) 
;;    (last (keys random-graph)) ; => list of nodes which is the shortest path by edge weight between the 2 nodes, or no path if one does not exist.

;; Assumptions:
;; 

(ns graph-traversal.q3
  (:require
   [clojure.pprint :as pprint])
  ; requre q2 make-graph
  (:require
   [graph-traversal.q2 :refer
    [make-graph]])

  )

(def SIMPLE_GRAPH
  {:A [[:B 2] [:D 8]],
   :B [[:A 2] [:D 5] [:E 6]],
   :C [[:F 3] [:E 9]],
   :D [[:A 8] [:B 5] [:E 3] [:F 2]]
   :E [[:D 3] [:F 1] [:C 9] [:B 6]]
   :F [[:C 3] [:E 1] [:D 2]]})             

;; (def SIMPLE_GRAPH
;;   {:1 [[:2 4] [:3 2]],
;;    :2 [[:3 3] [:4 2] [:5 3]],
;;    :3 [[:2 1] [:5 5] [:4 4]],
;;    :4 []
;;    :5 [[:4 1]]})

(def GRAPH_15_100
  {:14 [[:5 97] [:6 52] [:7 11] [:8 53] [:11 84] [:13 0]],
   :12 [[:6 13] [:13 87] [:14 12] [:5 81]],
   :11 [[:13 55] [:4 0]],
   :10 [[:9 18] [:5 59] [:14 22] [:6 26] [:12 94] [:7 98] [:0 73] [:1 20]],
   :13 [[:2 80] [:14 35] [:11 51] [:8 4] [:5 79] [:9 14] [:3 94]],
   :0
   [[:14 71]
    [:13 40]
    [:12 33]
    [:11 78]
    [:10 13]
    [:9 54]
    [:8 34]
    [:7 56]
    [:6 23]
    [:5 72]
    [:4 43]
    [:3 34]
    [:2 37]
    [:1 87]],
   :4 [[:8 21] [:6 75] [:12 9] [:0 21] [:2 19] [:11 72] [:9 79] [:7 19] [:1 46]],
   :7 [[:3 39] [:9 61] [:5 91] [:14 15] [:11 79] [:0 32]],
   :1 [[:9 47] [:0 73] [:14 88] [:10 19] [:7 42] [:6 73] [:3 49]],
   :8 [[:9 15] [:10 34] [:7 93] [:6 83] [:2 87] [:14 20]],
   :9 [[:3 19] [:7 98] [:4 95] [:0 38]],
   :2 [[:9 11] [:11 9] [:12 18] [:7 28] [:10 8] [:14 39] [:13 60]],
   :5 [[:10 14] [:13 67] [:8 68] [:1 17] [:2 6] [:0 6] [:12 4]],
   :3 [[:4 45] [:14 55] [:0 83] [:10 57] [:11 84]],
   :6 [[:0 90] [:4 58] [:9 34] [:10 32] [:2 52] [:1 8] [:5 85] [:14 60]]})

(defn init_shortest_path_table
  "Given a graph and a start node, return a map of all keys at max distance and the start node with distance 0"
  [graph start_node]
  (->> graph
       keys
       (remove (partial = start_node))
       (reduce
        (fn [acc x]
          (assoc acc x
                 {:distance Integer/MAX_VALUE
                  :path []}))
        {start_node {:distance 0 :path [start_node]}
        ;;  :radius Integer/MAX_VALUE
        ;;  :diameter Integer/MIN_VALUE
         })))

;; for each neighbouring node, get the current distance to it in the shortest_path_table
;; if the current distance is greater than the distance to the current node + weight of the edge then
;; we update the shortest_path_table with the new distance and path
(defn- update-table [shortest_path_table neighbours current_node]
  (reduce
   (fn [acc [neighbour_node neighbour_weight]]
     (let [current_distance_to_neighbour (get-in acc [neighbour_node :distance] Integer/MAX_VALUE)
           distance_to_current_node (get-in acc [current_node :distance] Integer/MAX_VALUE)
           path_to_current_node (get-in acc [current_node :path] [])
          ;;  current_distance_to_neighbour (:distance (acc neighbour_node))
          ;;  distance_to_current_node (:distance (acc current_node))
          ;;  path_to_current_node (:path (acc current_node))
           new_distance_to_neighbour (+ distance_to_current_node neighbour_weight)           
          ;;  _ (println "update-table node:" neighbour_node "weight:" neighbour_weight)
          ;;  _ (println "current_distance:" current_distance_to_neighbour)
          ;;  _ (println "new_distance:" new_distance_to_neighbour)
          ;;  _ (pprint/pprint acc)
           ]
       ; NOTE: If the distance of both paths are the same, we do not change the path
       (-> acc
           (cond-> (> current_distance_to_neighbour new_distance_to_neighbour)
             (assoc neighbour_node
                    {:distance new_distance_to_neighbour
                     :path (conj path_to_current_node neighbour_node)}))
          ;;  (cond-> (and (> (:radius acc) new_distance_to_neighbour)
          ;;               (> current_distance_to_neighbour new_distance_to_neighbour))
          ;;    (assoc :radius new_distance_to_neighbour))
          ;;  (cond-> (and (< (:diameter acc) new_distance_to_neighbour) 
          ;;               (> current_distance_to_neighbour new_distance_to_neighbour))
          ;;    (assoc :diameter new_distance_to_neighbour)))
       )))
   shortest_path_table neighbours))

(defn get-nearest-unvisited-node [_ []] [])
(defn get-nearest-unvisited-node [table unvisited_nodes]
  (reduce
   (fn [nearest_node current_node]
     (let [nearest_distance (:distance (table nearest_node))
           current_node_distance (:distance (table current_node))]
       (if (< current_node_distance nearest_distance)
         current_node
         nearest_node)))
   (first unvisited_nodes)
   (rest unvisited_nodes)))

(defn step [_ node [] table visited]
  {:next_node node
   :unvisited_nodes []
   :table table
   :visited_nodes visited})

(defn step [graph
            current_node
            unvisited_nodes
            shortest_path_table
            visited_nodes]
  (let [;
        neighbours (graph current_node)
        updated_visited_nodes (conj visited_nodes current_node)
        updated_unvisited_nodes (remove (partial = current_node) unvisited_nodes)
        ;; _ (println "current_node:" current_node)
        ;; _ (println "neighbours:" neighbours)
        ;; _ (println "next_unvisited_nodes:" updated_unvisited_nodes)

        updated_shortest_path_table (update-table
                                     shortest_path_table
                                     neighbours
                                     current_node)
        next_node_to_visit (get-nearest-unvisited-node
                            updated_shortest_path_table
                            updated_unvisited_nodes)
        ;; _ (println "next_node_to_visit:" next_node_to_visit)
        ]
    {:next_node next_node_to_visit
     :unvisited_nodes updated_unvisited_nodes
     :table updated_shortest_path_table
     :visited_nodes updated_visited_nodes}))

;; Algorithm follows: https://www.youtube.com/watch?v=bZkzH5x0SKU
;; each node is visited once, function terminates when either:
;; all (keys graph) are visited
;; OR
;; no more neighbours left to visit (ie remaining unvisited nodes are not connected)  
(defn shortest-path-table
  ([graph start_node]
   (shortest-path-table graph start_node nil false))

  ([graph start_node end_node]
   (shortest-path-table graph start_node end_node false))

  ([graph start_node end_node debug]
   (loop [current_node start_node
          unvisited_nodes (keys graph)
          shortest_path_table (init_shortest_path_table graph start_node)
          visited_nodes []]
     (if (or (empty? unvisited_nodes)
             (and (not (nil? end_node))
                  (some #(= end_node %) visited_nodes)))
      ;; Return the path to end_node from the final table
       (do
         (when debug (pprint/pprint shortest_path_table))
         shortest_path_table)

       (let [{:keys [next_node unvisited_nodes table visited_nodes]}
             (step graph current_node unvisited_nodes shortest_path_table visited_nodes)]
         (recur next_node
                unvisited_nodes
                table
                visited_nodes))))))


(defn shortest-path
  ([graph start_node end_node]
   (shortest-path graph start_node end_node false))

  ([graph start_node end_node debug]
   (let [all_paths (shortest-path-table graph start_node end_node debug)
         shortest_path (get-in all_paths [end_node :path])]
     shortest_path)))



(shortest-path SIMPLE_GRAPH :A :C true)

;; (def random-graph (make-graph 10 10))

;; (shortest-path (make-graph 10 80)
;;                (first (keys random-graph))
;;                (rand-nth (vec (keys random-graph))))

;; (init_shortest_path_table SIMPLE_GRAPH :A)

(shortest-path GRAPH_15_100 :12 :13)
