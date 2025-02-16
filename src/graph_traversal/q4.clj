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
  


(defn eccentricity [graph node]
  
  )
(defn radius [graph])
(defn diameter [graph])

(eccentricity {} :1)
(radius {})
(diameter {})