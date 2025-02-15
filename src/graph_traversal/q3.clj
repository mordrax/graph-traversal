(ns graph-traversal.q3
  (:require
   [clojure.pprint :as pprint]))

;; (def SIMPLE_GRAPH
;;   {:1 [[:2 4] [:3 2]],
;;    :2 [[:3 3] [:4 2] [:5 3]],
;;    :3 [[:2 1] [:5 5] [:4 4]],
;;    :4 []
;;    :5 [[:4 1]]})

(def SIMPLE_GRAPH
  {:A [[:B 2] [:D 8]],
   :B [[:A 2] [:D 5] [:E 6]],
   :C [[:F 3] [:E 9]],
   :D [[:A 8] [:B 5] [:E 3] [:F 2]]
   :E [[:D 3] [:F 1] [:C 9] [:B 6]]
   :F [[:C 3] [:E 1] [:D 2]]})

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
        {start_node {:distance 0 :path []}})))

;; for each neighbouring node, check the current shortest path to that node
;; if the current path is shorter than the existing path, update the shortest path table
(defn- update_shortest_path_table [shortest_path_table neighbours]
  (reduce
   (fn [acc [node weight]]
     (let [current_distance (:distance (acc node))
           new_distance weight
           _ (println "node:" node "weight:" weight)
           _ (pprint/pprint acc)
           _ (println "current_distance:" current_distance)
           _ (println "new_distance:" new_distance)]
       (if (< new_distance current_distance)
         (assoc acc node
                {:distance new_distance
                 :path (conj (:path (acc node)) node)})
         acc)))
   shortest_path_table neighbours))

(defn get-nearest-unvisited-node [_ []] [])
(defn get-nearest-unvisited-node [updated_shortest_path_table updated_unvisited_nodes]
  (reduce
   (fn [nearest_node x]
     (let [current_nearest (:distance nearest_node)
           x_distance (:distance (updated_shortest_path_table x))
           _ (println "x:" x)
           _ (println "current_distance:" current_nearest)
           _ (println "new_distance:" x_distance)]
       (if (< x_distance current_nearest)
         x
         nearest_node)))
   (get updated_shortest_path_table (first updated_unvisited_nodes))
   (rest updated_unvisited_nodes)))


(defn shortest-path-helper [graph
                            current_node
                            unvisited_nodes
                            shortest_path_table
                            visited_nodes]
  (lazy-seq
   (if (empty? unvisited_nodes)
          ;; all nodes have been visited
          ;; return shortest path to end_node
     shortest_path_table
     (let [neighbours (graph current_node)
           updated_shortest_path_table (update_shortest_path_table shortest_path_table neighbours)
           updated_unvisited_nodes (remove (partial = current_node) unvisited_nodes)
           next_node_to_visit (get-nearest-unvisited-node updated_shortest_path_table updated_unvisited_nodes)
           updated_visited_nodes (conj visited_nodes current_node)
           _ (do
               (println "current_node:" current_node)
               (println "neighbours:" neighbours)
               (println "next_unvisited_nodes:" updated_unvisited_nodes)
               (println "shortest_path_acc:")
               (pprint/pprint updated_shortest_path_table))]
       (cons updated_shortest_path_table
             (shortest-path-helper graph
                                   next_node_to_visit
                                   updated_unvisited_nodes
                                   updated_shortest_path_table
                                   updated_visited_nodes))))))

;; Algorithm follows: https://www.youtube.com/watch?v=bZkzH5x0SKU
;; each node is visited once, function terminates when either:
;; all (keys graph) are visited
;; OR
;; no more neighbours left to visit (ie remaining unvisited nodes are not connected)
(defn shortest-path
  "Find shortest path between start_node and end_node using Dijkstra's algorithm"
  [graph start_node end_node]
  (let [current_node start_node
           ;; 1: Mark all nodes as unvisited
        unvisited_nodes (keys graph)

           ;; 2. Assign weight of all nodes other than start_node to Integer/MAX_VALUE
           ;; assign start_node weight 0
        shortest_path_table (init_shortest_path_table graph start_node)
        visited_nodes []]
    (shortest-path-helper graph
                          current_node
                          unvisited_nodes
                          shortest_path_table
                          visited_nodes)))

(take 5 (shortest-path SIMPLE_GRAPH :A :C))

(println "hi")
;; (init_shortest_path_table SIMPLE_GRAPH :A)