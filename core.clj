(ns gui3.core
  (:require [seesaw.core]
            [seesaw.graphics]
            [seesaw.mouse])
  (:gen-class))

(def xy (ref [0,0]))

(defn paintit [c g] (seesaw.graphics/anti-alias
                      (seesaw.graphics/draw g
                       (seesaw.graphics/circle (nth @xy 0) (nth @xy 1) 10) 
                       (seesaw.graphics/style :foreground :red 
                                              :stroke (seesaw.graphics/stroke :width 8))))) 

(defn make-frame [] 
  (seesaw.core/frame 
           :on-close :exit
           :title "Bogus!"
           :size [400 :by 400] 
           :content 
           (seesaw.core/canvas :id :canvas :background :black :paint paintit)))
                          
(defn behave [root]
  (let [canvas (seesaw.core/select root [:#canvas])
        entered (seesaw.core/listen canvas :mouse-entered #(seesaw.core/config! % :background :black))
        exited (seesaw.core/listen canvas :mouse-exited #(seesaw.core/config! % :background :blue))
        moves (seesaw.core/listen canvas :mouse-moved  (fn [e](dosync (ref-set xy (seesaw.mouse/location e)))
                                                              (seesaw.core/config! canvas :paint paintit)))]
        root))

(defn -main [& args]
  (-> (make-frame)
      behave
      seesaw.core/show!))

