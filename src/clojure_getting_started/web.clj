(ns clojure-getting-started.web
  (:require [compojure.core :refer :all]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pr-str ["Hello" :from 'Heroku])})

(defroutes app
  (POST "/" []
       {:body "{text: '< Hello, world!!'}"
        :headers {"Content-Type" "application/json"}})
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site app) {:port port :join? false :security false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
