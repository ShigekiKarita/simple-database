(ns simple-database.core-test
  (:require [clojure.test :refer :all]
            [simple-database.core :refer :all]))

(deftest album-test
  (testing "construction"
    (is (.equals
         (->album "Roses" "Kathy Mattea" 7 true)
         {:title "Roses", :artist "Kathy Mattea", :rating 7, :ripped true})))
  ;; (testing "save/load"
  ;;   (add-album (->album "Roses" "Kathy Mattea" 7 true))
  ;;   (add-album (->album "Hoge" "Fuga" 9 false))
  ;;   (def pre )
  ;;   (is )
      )
