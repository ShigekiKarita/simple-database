(ns simple-database.core
  (:require [clojure.pprint :refer [print-table]]
            [clojure.java.io :as io]))


(defrecord album [title artist rating ripped])

(def ^:private *db* (ref []))

(defn dump-db []
   (print-table  @*db*))                ; very cool

(defn prompt-read [prompt]
  (do
    (print prompt ":\t")
    (flush)
    (def line (read-line))
    (println)
    line))

(defn y-or-n-p [s]
  (boolean
   (some true?
         (map #(= % s)
              ["" "y" "Y" "yes" "true"]))))

(defn prompt-for-album []
  (album.
   (prompt-read "title")
   (prompt-read "artist")
   (Integer/parseInt (prompt-read "rating"))
   (y-or-n-p (prompt-read "ripped: [y/n]"))))

(defn add-album
  ([] (add-album (prompt-for-album)))
  ([album]
   (dosync                              ; in STM
    (commute *db* conj album))))

(defn- resource-path
  [path]
  (-> path io/resource .getPath))

(defn save-db [filename]
  (spit (resource-path filename) @*db*))

(defn load-db [filename]
  (dosync
   (ref-set
    *db*
    (read-string (slurp (resource-path filename))))))
