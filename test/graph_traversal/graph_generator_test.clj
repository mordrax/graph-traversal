(ns graph-traversal.graph-generator-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [graph-traversal.graph-generator :as g]))

(deftest generate-graph-test
  ;; (testing "generate-graph with valid input"
  ;;   (let [n 5
  ;;         s 9
  ;;         graph (g/generate-graph n s)
  ;;         edges (vec graph)]  ; Convert to vector for easier testing

  ;;     ; Test the overall structure
  ;;     (is (set? graph) "Should return a set")
  ;;     (is (= n (count graph)) "Should contain N elements")

  ;;     ; Test each edge explicitly
  ;;     (is (every? vector? edges) "All elements should be vectors")
  ;;     (is (every? #(= 2 (count %)) edges) "All edges should have exactly 2 elements")
  ;;     (is (every? #(integer? (second %)) edges) "All weights should be integers")

  ;;     ; Test specific edge properties
  ;;     (doseq [edge edges]
  ;;       (let [[node weight] edge]
  ;;         (is (keyword? node) "Node should be a keyword")
  ;;         (is (integer? weight) "Weight should be an integer")
  ;;         (is (>= weight 0) "Weight should be non-negative")
  ;;         (is (< weight 100) "Weight should be less than 100")))))

  (testing "generate-graph with invalid sparsity"
    (let [invalid-inputs [[0 0] [0 1] [1 1] [2 3] [3 7]]  ; List of [N S] pairs
          ]
      (doseq [input invalid-inputs]
        (let [n (first input)
              s (second input)
              graph (g/generate-graph n s)]
          (is (nil? graph) (str "Should return nil for invalid sparsity with N=" n " and S=" s)))))

    (testing "generate-graph with valid sparsity"
      (let [valid-inputs [[2 1] [2 2] [3 2] [3 5] [10 90]] ; Valid inputs
            ]
        (doseq [input valid-inputs]
          (let [n (first input)
                s (second input)
                graph (g/generate-graph n s)]
            (is (not (nil? graph)) (str "Should not return nil for valid sparsity with N=" n " and S=" s))))))))