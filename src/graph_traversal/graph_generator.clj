(ns graph-traversal.graph-generator
  (:require [clojure.set :as set]))

(def WEIGHT 100)

(defn num-to-keyword [x]
  (keyword (str x)))

(defn add-weight [x]
  [(num-to-keyword (inc x)) (rand-int WEIGHT)])

(defn check-sparseness-constraint
  "Checks that N - Number of nodes and S - Sparsity value are sane. 
   Requires S to be between N-1 and N(N-1) inclusive."
  [N S]
  (let [max-connections (dec N)
        lower-bound max-connections
        upper-bound (* N max-connections)]
    (cond (<= N 1)
          (do
            (println "N must be greater than 1. N:" N)
            false)
          (or (< S lower-bound) (> S upper-bound))
          (do
            (println "Sparsity value must be from" lower-bound "to" upper-bound "inclusive. N:" N ", S:" S)
            false)

          :else
          true)))

(defn generate-graph [N S]
  (when (check-sparseness-constraint N S)
    (->> (range N)
         (map add-weight)
         set)))