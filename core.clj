(ns gui3.core
  (:require [seesaw.core]
            [seesaw.graphics]
            [seesaw.mouse])
  (:gen-class))

(def xy (ref []))

(defn paintit [c g] (if (> (count @xy) 0)
                     (loop [k (- (count @xy) 1)]
                       (when (> k -1)
                        (seesaw.graphics/anti-alias
                         (seesaw.graphics/draw g
                          (seesaw.graphics/circle (nth (nth @xy k) 0) (nth (nth @xy k) 1) 5) 
                          (seesaw.graphics/style :foreground :green 
                                                 :stroke (seesaw.graphics/stroke :width 10))))
                        (recur (- k 1))))
                     (seesaw.graphics/draw g)
                     )) 

(defn make-frame [] 
  (seesaw.core/frame 
           :on-close :exit
           :title "Bogus!"
           :size [400 :by 400] 
           :content 
           (seesaw.core/canvas :id :canvas :background :green :paint paintit)))
                          
(defn behave [root]
  (let [canvas (seesaw.core/select root [:#canvas])
        entered (seesaw.core/listen canvas :mouse-entered (fn [e] (seesaw.core/config! canvas :background :black)))
        exited (seesaw.core/listen canvas :mouse-exited (fn [e] (seesaw.core/config! canvas :background :green)
                                                                (dosync (ref-set xy []))
                                                                (seesaw.core/repaint! canvas)))
        moves (seesaw.core/listen canvas :mouse-moved  (fn [e](dosync (ref-set xy (conj @xy (seesaw.mouse/location e))))
                                                              (seesaw.core/repaint! canvas)))]
        root))

(defn -main [& args]
  (-> (make-frame)
      behave
      seesaw.core/show!))
