(defproject graph-traversal "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories [["clojars" "https://repo.clojars.org/"]]
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :source-paths ["src"]
  :test-paths ["test"]
  :plugins [[com.jakemccrary/lein-test-refresh "0.25.0"]]
  :test-refresh {:quiet true
                 :changes-only true}
  :main ^:skip-aot graph-traversal.core
  :target-path "target/%s"
  :profiles

  {:uberjar {:aot :all
             :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
   :test {:dependencies [[org.clojure/test.check "1.1.1"]]}
   :dev {:dependencies [[cider/cider-nrepl "0.28.2"]]}})