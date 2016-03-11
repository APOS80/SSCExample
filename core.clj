(ns gui3.core
  (:require [seesaw.core]
            [seesaw.graphics]
            [seesaw.mouse])
  (:gen-class))

(defn make-frame [] 
  (seesaw.core/frame 
           :on-close :exit
           :title "Bogus!"
           :resizable? true
           :size [400 :by 400] 
           :content 
           (seesaw.core/canvas :id :canvas :background :black :focusable? true)))
                          
(defn behave [root]
  (let [canvas (seesaw.core/select root [:#canvas])
        entered (seesaw.core/listen canvas :mouse-entered (fn [e] (println "In!")))
        exited (seesaw.core/listen canvas :mouse-exited (fn [e] (println "Out!")))
        moves (seesaw.core/listen canvas :mouse-moved (fn [e] (println (seesaw.mouse/location e)))) 
        ]
        root))


(defn -main [& args]
  (-> (make-frame)
      behave
      seesaw.core/show!))
