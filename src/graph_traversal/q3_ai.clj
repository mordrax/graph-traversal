;; This is claude's solution, confirmed it works with SIMPLE_GRAPH example
;; Abandoned because it's not my solution, and I don't understand it.

(ns graph-traversal.q3-ai
  (:require [clojure.set :as set]))

(defn get-path-weight
  "Calculate total weight of a path using the graph's edge weights"
  [graph path]
  (if (< (count path) 2)
    0
    (loop [total 0
           [node1 node2 & rest] path]
      (if (nil? node2)
        total
        (let [edge-weight (->> (graph node1)  ; Get neighbors of current node
                               (filter #(= (first %) node2))  ; Find edge to next node
                               first  ; Get the [node weight] pair
                               second)]  ; Get the weight
          (recur (+ total edge-weight)
                 (cons node2 rest)))))))

(defn get-neighbors
  "Get unvisited neighbors with their cumulative distances"
  [graph current-distance current-node unvisited]
  (for [[next-node weight] (graph current-node)
        :when (contains? unvisited next-node)]
    [next-node (+ current-distance weight)]))

(defn shortest-path
  "Find shortest path between start_node and end_node using Dijkstra's algorithm"
  [graph start_node end_node]
  (loop [unvisited (set (keys graph))  ; Set of unvisited nodes
         distances (assoc (zipmap (keys graph) (repeat Integer/MAX_VALUE))
                          start_node 0)  ; Map of shortest distances
         predecessors {}  ; Map of predecessor nodes
         current start_node]
    (if (or (empty? unvisited)  ; No more nodes to visit
            (= current end_node)  ; Reached destination
            (= (distances current) Integer/MAX_VALUE))  ; No path exists
      ;; Build path from predecessors map
      (if (contains? predecessors end_node)
        (loop [path [end_node]
               current end_node]
          (if-let [pred (predecessors current)]
            (recur (cons pred path) pred)
            path))
        nil)  ; No path exists
      (let [current-distance (distances current)
            ;; Update distances for all unvisited neighbors
            [new-distances new-predecessors]
            (reduce
             (fn [[dists preds] [node new-distance]]
               (if (< new-distance (dists node))
                 [(assoc dists node new-distance)
                  (assoc preds node current)]
                 [dists preds]))
             [distances predecessors]
             (get-neighbors graph current-distance current unvisited))
            ;; Remove current node from unvisited
            new-unvisited (disj unvisited current)
            ;; Find next node to visit (minimum distance among unvisited)
            next-node (when (seq new-unvisited)
                        (apply min-key new-distances new-unvisited))]
        (if next-node
          (recur new-unvisited
                 new-distances
                 new-predecessors
                 next-node)
          nil)))))

;; Example usage:
(def SIMPLE_GRAPH
  {:1 [[:2 4] [:3 2]],
   :2 [[:3 3] [:4 2] [:5 3]],
   :3 [[:2 1] [:5 5] [:4 4]],
   :4 []
   :5 [[:4 1]]})

(comment
  (shortest-path SIMPLE_GRAPH :1 :5)
  ;; => (:1 :3 :2 :4)  ; Example output

  (get-path-weight SIMPLE_GRAPH
                   (shortest-path SIMPLE_GRAPH :1 :4))
  ;; => 5  ; Total weight of the shortest path

  (shortest-path {:1 [[:2 1]],
                  :2 []
                  } :1 :2)
  )
