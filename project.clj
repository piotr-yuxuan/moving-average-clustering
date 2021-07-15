(defproject com.github.piotr-yuxuan/moving-average-clustering (-> "./resources/moving-average-clustering.version" slurp .trim)
  :description ""
  :url "https://github.com/piotr-yuxuan/moving-average-clustering"
  :license {:name "European Union Public License 1.2 or later"
            :url "https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12"
            :distribution :repo}
  :scm {:name "git"
        :url "https://github.com/piotr-yuxuan/moving-average-clustering"}
  :pom-addition [:developers [:developer
                              [:name "胡雨軒 Петр"]
                              [:url "https://github.com/piotr-yuxuan"]]]
  :dependencies [;; Application domain
                 [metosin/malli "0.5.1"] ; Coerce shape and (de)code data
                 [org.clojure/math.combinatorics "0.1.6"]

                 ;; Ancillary tools
                 [camel-snake-kebab "0.4.2"] ; Case and type manipulation
                 ]
  :main piotr-yuxuan.moving-average-clustering.main
  :profiles {:github {:github/topics []}
             :provided {:dependencies [[org.clojure/clojure "1.10.3"] ; Clojure language
                                       ]}
             :dev {:global-vars {*warn-on-reflection* true}
                   :dependencies []
                   :source-paths ["src"]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.disable-locals-clearing=false"
                                  "-Dclojure.compiler.direct-linking=true"]}
             :kaocha [:test {:dependencies [[lambdaisland/kaocha "1.0-612"]]}]}
  :repositories [["jamm" {:url "https://maven.pkg.github.com/piotr-yuxuan/jamm"
                          :username :env/GITHUB_ACTOR
                          :password :env/WALTER_GITHUB_PASSWORD}]]
  :deploy-repositories [["clojars" {:sign-releases false
                                    :url "https://clojars.org/repo"
                                    :username :env/WALTER_CLOJARS_USERNAME
                                    :password :env/WALTER_CLOJARS_PASSWORD}]
                        ["github" {:sign-releases false
                                   :url "https://maven.pkg.github.com/piotr-yuxuan/moving-average-clustering"
                                   :username :env/GITHUB_ACTOR
                                   :password :env/WALTER_GITHUB_PASSWORD}]])
