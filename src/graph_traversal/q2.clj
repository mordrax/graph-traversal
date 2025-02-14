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

(defn check-constraints
  "Checks N > 0, S >= N-1, S <= N(N-1) and returns "
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

;; make-graph should perform the following functions, short-circuting 
;; if any of the functions return falsey
;; - Check the sparseness constraint
;; - Generate a list of nodes for connectedness
;; - Generate a list of all possible edges
;; - Generate a list of random edges
(defn make-graph [N S]
  (when (check-constraints N S)
    (->> (range N)
         (map add-weight)
         set)))

(flatten (make-graph 5 20)) ; nil
   