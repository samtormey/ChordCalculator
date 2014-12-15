(def pitches [:C :C# :D :D# :E :F :F# :G :G# :A :A# :B])
(def pitch_num (zipmap pitches (range 12)))
(def num_pitch (zipmap (range 12) pitches))
(defn major-third [bass] (num_pitch (mod (+ (pitch_num bass) 4) 12)))
(defn minor-third [bass] (num_pitch (mod (+ (pitch_num bass) 3) 12)))

;(defn majortriad [bass]
;  [ bass (major-third bass) (minor-third (major-third bass)) ])

;(defn minortriad [bass]
;  [ bass (minor-third bass) (major-third (minor-third bass)) ])

;(map major pitches)




;; Major takes a keyname for the bass note and a number for how many
;; pitches you want (if num == 4, then Major returns the Major 7th chord).


(defn Major [bass num]
    (loop [num num
           currentpitch bass
           chord [bass]
           now-maj? true]

        (if (= num 1)
          chord
          (let [nextpitch
               (if (= true now-maj?)
                 (major-third currentpitch)
                 (minor-third currentpitch))]
              (recur
                (- num 1)
                nextpitch
                (conj chord nextpitch)
                (not now-maj?))
        )
      )
    )
)

(Major :C 3) ;;=> [:C :E :G]
(Major :D 4) ;;=> [:D :F# :A :C#]





















