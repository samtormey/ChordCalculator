(def pitches [:C :C# :D :D# :E :F :F# :G :G# :A :A# :B])
(def pitch_num (zipmap pitches (range 12)))
(def num_pitch (zipmap (range 12) pitches))
(defn major-third [bass] (num_pitch (mod (+ (pitch_num bass) 4) 12)))
(defn minor-third [bass] (num_pitch (mod (+ (pitch_num bass) 3) 12)))


(def threes [3 3 3 3 3 3 3 3 3 3 3 3])  ;; gotta be a better way than this
(def trues  [true true true true true true true true true true true true])
(def falses [false false false false false false false false false false false false])
(def major-triads (zipmap (map set (map Chord pitches threes trues)) pitches))
(def minor-triads (zipmap (map set (map Chord pitches threes falses)) pitches))

;; Major takes a keyname for the bass note and a number for how many
;; pitches you want (if num == 4, then Major returns the Major 7th chord).

;;(Chord :C 4 true) => [:C :E :G]
;;(Chord :D 4 true) => [:D :F# :A :C#]


(defn Chord [bass num maj?]
    (loop [num num
           currentpitch bass
           chord [bass]
           now-maj? maj?]

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


;; for now, assume that they are giving us a decently valid triad
;; below is a prototype for how we can search, can easily extend to include
;; major-sevenths, minor-sevenths, major-ninths, minor-ninths.

(def testinput [:A :C# :F#])
(defn FindChord [testyo]
(if (= (major-triads (set testyo)) nil)
  (if (= (minor-triads (set testyo)) nil) 
    "Not a valid triad" 
    [(minor-triads (set testyo)) 
     (-(count testyo) ((zipmap testyo (range (count testyo))) (minor-triads (set testyo))))
     false])
  [(major-triads (set testyo)) 
     (-(count testyo) ((zipmap testyo (range (count testyo))) (major-triads (set testyo))))
     true]))


(FindChord testinput) ;;=> [:D# 2 true], thus this is a D# minor chord (which I 
                      ;;   need to add functionality to), 2nd inversion, and 'true'(ly) a major
                      ;;   chord.














;(defn FindChord [input] 
;      (loop [cnt 0
;            currentcheck (first input)
;            inversion 0]
;      (if (> inversion (count input)))
;      (if (= (Chord currentcheck (count testyo) true) testyo)
;          [currentcheck inversion true]
;          (if (= (Chord currentcheck (count testyo) false) testyo)
;              [currentcheck inversion false]
;              (recur
;                (inc cnt)
;                (nth input (inc cnt))
;                (inc inversion))))))



;;(defn major-triad [bass]
;;  #{ bass (major-third bass) (minor-third (major-third bass)) })

;;(defn minor-triad [bass]
;; #{ bass (minor-third bass) (major-third (minor-third bass)) })













