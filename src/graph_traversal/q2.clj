;; 2. Write an algorithm to randomly generate a simple directed graph using your answer from #1
;; Such that
;; #!text
;; Input:
;;     N - size of generated graph
;;     S - sparseness (number of directed edges actually; from N-1 (inclusive) to N(N-1) (inclusive))
;; Output:
;;     simple connected graph G(n,s) with N vertices and S edges
;; Please ensure that your graph is connected, otherwise you won't be able to complete the following questions.
                           
;; Assumptions
;; - To ensure connectedness, the graph will connect one starting node to all other nodes. 
;;   Beyond this, all other edges will be pseudo random.
;; - To generate random edges with no other bias, we will create an exhausive collection of
;;   all possible edges N(N-1)-(N-1) because we've already created N-1 edges to ensure connectedness.
;;   and then generate S-(N-1) unique position from this collection.
;; - There is no metion of what the weight is. There is only the hint to use the answer from q1. which
;;   contains weight and also q3. So I'm _assuming_ the simple graph will contain weight. AND
;;   I'm _assuming_ the weight will be set as a global constant of 0-99 for simplicity.
;; - Function will return nil if the sparsity constraint is not met.

(ns graph-traversal.q2
  (:require [clojure.set :as set]))

(def WEIGHT 100)
(defn make-weight [] (rand-int WEIGHT))

(defn num-to-keyword [x]
  (keyword (str x)))

(defn add-weight [x]
  [(num-to-keyword (inc x)) (rand-int WEIGHT)])

(defn is-valid-graph
  "Checks N > 0, S >= N-1, S <= N(N-1) and returns falsey if any of the constraints are not met"
  [N S]
  (let [max-connections-node (dec N)
        lower-bound max-connections-node
        upper-bound (* N max-connections-node)]
    (cond (< N 1)
          (do
            (println "N must be 1 or more. N:" N)
            false)
          (or (< S lower-bound) (> S upper-bound))
          (do
            (println "Sparsity value must be from" lower-bound "to" upper-bound "inclusive. N:" N ", S:" S)
            false)

          :else
          true)))

#_(
  ;;  Edges are thought of as a exhausive 2D grid
  ;;  eg, N = 5, each row starts with the first column being a node and each
  ;;  subsequent column is a edge to another node. So a fully connected N=5 graph
  ;;  would look like
  ;;  0 | 1 2 3 4
  ;;  1 | 0 2 3 4 
  ;;  2 | 0 1 3 4 
  ;;  3 | 0 1 2 4
  ;;  4 | 0 1 2 3
   
  ;;  All 20 cells (representing an edge E) is by definition
  ;;  the complete set of possible edges for a fully connected graph of N nodes.

  ;;  Therefore, for a given number (edge-index) between 1 and 20, we can represent all possible
  ;;  edges in the graph.
  ;;  eg, A connected graph is just edges 0 to 3
  ;;  A random connected graph could be 0,1,2,3,4,7,16,19
  ;;  eg, N=5, 4 neighbours, edge 19
  ;;  row = floor(19 / 4) = 4, starting node is 4
  ;;  col = 19 % 4 = 3, 3rd index in neighbours.
  ;;  For node 4, neighbours are [0 1 2 3], 3rd is 3.
  ;;  so row = 4, col = 3

  ;;  This function will take a graph size and edge-index and return the absolute edge. 
) 

(defn absolute-edge-from-graph [N edge-index]
  (when (and (> N 1) (>= edge-index 0))
  (let [max-neighbours (dec N)
        row (quot edge-index max-neighbours)
        row-edge-index (rem edge-index max-neighbours)
        neighbours (remove (fn [x] (= row x)) (range N))
        col (get (vec neighbours) row-edge-index)
          ;; _ (do
          ;;    (println "N:" N)
          ;;    (println "edge-index:" edge-index)
          ;;    (println "max-neighbours:" max-neighbours)
          ;;    (println "row:" row)
          ;;    (println "row-edge-index:" row-edge-index)
          ;;    (println "neighbours:" neighbours)
          ;;    (println "col:" col)
          ;; )
        ]
    [row col])  
    ) 
  )
  

;; N = 5
;; S = 20
;; Distinct edge E 
;; = floor (E / N) to (E % N)
;; Edge 1: 0 to 1
;; Edge 5: 1
(defn make-graph [N S]
  (when (is-valid-graph N S)
    (let [max-neighbours (dec N)
          make-edge (partial absolute-edge-from-graph N)
          minimum-connected-edges (map make-edge (range max-neighbours))
          max-sparsity (* N max-neighbours)
          S-after-minimum-edges (->> (count minimum-connected-edges)
                                     (- S)
                                     (max 0))
          random-edges
          (take S-after-minimum-edges
                (shuffle (range (dec N) max-sparsity)))
          remaining-random-edge-indexes ()]
      minimum-connected-edges
    ))
)

(make-graph 5 20) ; nil

(take 19 (shuffle (range 0 20)))

(count (take 15 (shuffle (range 4 20))))

(map (partial absolute-edge-from-graph 5) (range 5))