;; 4. Write a suite of functions to calculate distance properties for your graph.
;; Now that you have implemented Dijkstra's algorithm you should be able to calculate the eccentricity of any vertex in your graph, and in turn the radius and diameter of your graph.
;; Please re-acquaint yourself with graph distance properties https://en.wikipedia.org/wiki/Distance_(graph_theory),

;; The eccentricity of a vertex v is defined as the greatest distance between v and any other vertex.
;; The radius of a graph is the minimum eccentricity of any vertex in a graph.
;; The diameter of a graph is the maximum eccentricity of any vertex in a graph.
;; I should be able to write something like this:

;; #!clojure
;; (def random-graph (make-graph 10 10))
;; (eccentricity random-graph (first (keys random-graph))) ; => number expressing eccentricity for `first` vertex in random-graph
;; (radius random-graph) ; => minimal eccentricity
;; (diameter random-graph) ; => maximal eccentricity

;; Assumptions:
;; - The graph is connected.


(ns graph-traversal.q4
  (:require
   [clojure.pprint :as pprint])
  (:require
   [graph-traversal.q3 :refer
    [shortest-path-table]]))
  
(def SIMPLE_GRAPH
  {:A [[:B 2] [:D 8]],
   :B [[:A 2] [:D 5] [:E 6]],
   :C [[:F 3] [:E 9]],
   :D [[:A 8] [:B 5] [:E 3] [:F 2]]
   :E [[:D 3] [:F 1] [:C 9] [:B 6]]
   :F [[:C 3] [:E 1] [:D 2]]})             

(defn max-distance [distances]
  (->> distances
       vals
       (map :distance)
       (reduce max 0)))  ; Handles empty map case, default 0

(defn min-distance [distances]
  (->> distances
       vals
       (map :distance)
       (reduce min 0)))  ; Handles empty map case, default 0

(defn eccentricity [graph node]
  (->> (shortest-path-table graph node)
       max-distance
       ))
  
(defn radius [graph]
  (->> (keys graph)
       (map #(eccentricity graph %))
       (reduce min Integer/MAX_VALUE)))  ; Handles empty graph case, default 0
  
(defn diameter [graph]
  (->> (keys graph)
       (map #(eccentricity graph %))
       (reduce max Integer/MIN_VALUE)))  ; Handles empty graph case, default 0
  

(eccentricity SIMPLE_GRAPH :A) ;12
(eccentricity SIMPLE_GRAPH :B) ;10
(eccentricity SIMPLE_GRAPH :C) ;12
(eccentricity SIMPLE_GRAPH :D) ;7
(eccentricity SIMPLE_GRAPH :E) ;8
(eccentricity SIMPLE_GRAPH :F) ;9
(radius SIMPLE_GRAPH) ;7
(diameter SIMPLE_GRAPH) ;12
(shortest-path-table SIMPLE_GRAPH :D)


(def G {:A {:distance 0, :path [:A]},
 :B {:distance 2, :path [:A :B]},
 :C {:distance 12, :path [:A :B :D :F :C]},
 :D {:distance 7, :path [:A :B :D]},
 :E {:distance 8, :path [:A :B :E]},
 :F {:distance 9, :path [:A :B :D :F]}}
)

(max-distance G)
(min-distance G)

(def GRAPH_10_70
  {:0 [[:1 29] [:2 47] [:3 75] [:4 25] [:5 24] [:6 6] [:7 4] [:8 97] [:9 81]],
   :4 [[:8 56] [:5 83] [:0 12] [:9 1] [:2 5] [:3 17]],
   :7 [[:9 58] [:3 3] [:0 39] [:6 98] [:1 8]],
   :1 [[:5 66] [:2 95] [:8 56] [:9 48] [:0 80] [:6 60] [:4 20]],
   :8 [[:6 85] [:4 34] [:9 32] [:2 25] [:0 84] [:7 69] [:5 55] [:3 47]],
   :9 [[:5 99] [:4 16] [:1 7] [:0 19] [:6 35] [:7 8] [:2 17]],
   :2 [[:6 55] [:1 8] [:8 17] [:3 7] [:0 34] [:9 15] [:4 76]],
   :5 [[:8 49] [:6 17] [:1 90] [:2 8] [:0 80] [:4 80] [:7 23]],
   :3 [[:6 65] [:4 8] [:9 84] [:2 47] [:7 96] [:0 13]],
   :6 [[:4 63] [:8 83] [:0 79] [:1 85] [:5 89] [:3 0] [:2 6] [:9 40]]})

(shortest-path-table GRAPH_10_70 :0)
(eccentricity GRAPH_10_70 :0) ; 29
(eccentricity GRAPH_10_70 :1) ; 56
(eccentricity GRAPH_10_70 :9) ; 43
(shortest-path-table GRAPH_10_70 :9)


(def random-graph (graph-traversal.q2/make-graph 10 70))
(eccentricity random-graph (first (keys random-graph))) ; => number expressing eccentricity for `first` vertex in random-graph
(radius random-graph) ; => minimal eccentricity
(diameter random-graph) ; => maximal eccentricity
