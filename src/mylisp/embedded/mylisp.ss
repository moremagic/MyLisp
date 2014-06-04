;;scheme 手習い(the Little Scheme) ---> #1 

;;#1 p61
(define add1 (lambda (n) (+ n 1)))

;;#1 p61
(define sub1 (lambda (n) (- n 1)))

;;自作
(define < (lambda (n m) (> m n)))

;;StackOverFlow
;;そのため GTanfunction.class + (define <) で代用してます
;;#1 p75
;;(define >
;;  (lambda (n m)
;;    (cond
;;      ((zero? n) #f)
;;      ((zero? m) #t)
;;      (else (> (sub1 n) (sub1 m))))))

;;#1 p75
;;(define <
;;  (lambda (n m)
;;    (cond
;;      ((zero? m) #f)
;;      ((zero? n) #t)
;;      (else (< (sub1 n) (sub1 m))))))

;;#1 p76
(define =
    (lambda (n m)
        (cond ((> n m) #f)
            ((< n m) #f)
            (else #t))))

;;#1 p76
(define expt
  (lambda (n m)
    (cond
      ((zero? m) 1)
      (else (* n (expt n (sub1 m)))))))

;;#1 p77
(define  /
    (lambda (n m)
        (cond ((< n m) 0)
            (else (add1 (/ (- n m) m))))))

;;#1 p78
(define length
    (lambda (lat)
        (cond ((null? lat) 0)
              (else (add1 (length (cdr lat)))))))

;;#1 p10
(define atom?
  (lambda (x) 
    (and (not (pair? x)) (not (null? x)))))

;;#1 p16, p19, 
(define lat?
  (lambda (n)
     (cond ((null? n) #t)
           ((atom? (car n)) (lat? (cdr n)))
           (else #f))))
;;#1 p23
(define member?
  (lambda (a lat)
    (cond ((null? lat) #f)
          ((eq? a (car lat)) #t)
          (else
           (member? a (cdr lat))))))

;;#1 p35, p38, p41
(define rember
  (lambda (a lat)
    (cond
      ((null? lat) '())
      ((eq? (car lat) a) (cdr lat))
      (else (cons (car lat)
            (rember a (cdr lat)))))))

;;#1 p80
(define all-nums
  (lambda (lat)
    (cond
      ((null? lat) '())
      ((number? (car lat)) (cons (car lat) (all-nums (cdr lat))))
      (else (all-nums (cdr lat))))))

;;#1 p80
(define eqan?
  (lambda (a1 a2)
    (cond
      ((and (number? a1) (number? a2))
        (= a1 a2))
      ((or (number? a1) (number? a2))
        #f)
      (else (eq? a1 a2)))))

;;#1 p95
(define equal?
  (lambda (s1 s2)
    (cond
      ((and (atom? s1) (atom? s2)) (eqan? s1 s2))
      ((or (atom? s1) (atom? s2)) #f)
      (else (eqlist? s1 s2)))))

;;#1 p96
(define eqlist?
  (lambda (l1 l2)
    (cond
      ((and (null? l1) (null? l2)) #t)
      ((or (null? l1) (null? l2)) #f)
      (else
        (and (equal? (car l1) (car l2))
          (eqlist? (cdr l1) (cdr l2)))))))

;;#1 p129
(define eq?-c
  (lambda (a)
    (lambda (x)
      (eq? x a))))

;;#1 p129
(define eq?-c
  (lambda (a)
    (lambda (x)
      (eq? x a))))

(define even? 
    (lambda (n)
      (if (= n 0) #t
             (odd? (- n 1)))))

(define odd?
    (lambda (n)
      (if (= n 0) #f
             (even? (- n 1)))))

(define string=?
    (lambda (s1 s2)
        (cond ((and (string? s1) (string? s2))
                (eq? s1 s2))
                (else #f))))

(define eqv?
    (lambda (obj1 obj2)
        (cond ((and (boolean? obj1) (boolean? obj2)) #t)
            ((and (symbol? obj1) (symbol? obj2))
                (string=? (symbol->string obj1)
                    (symbol->string obj2)))
            (else #f))))

(define (map f lst)
  (cond ((pair? lst)
         (cons (f (car lst)) (map f (cdr lst))))
        ((null? lst)
         '())
        (else
         (display "Not list"))))

(define insertR
  (lambda (new old lat)
    (cond ((null? lat) '())
          ((eq? old (car lat)) (cons old (cons new (cdr lat))))
          (else (cons (car lat) (insertR new old (cdr lat)))))))

(define insertL
  (lambda (new old lat)
    (cond ((null? lat) '())
          ((eq? old (car lat)) (cons new (cons old (cdr lat))))
          (else (cons (car lat) (insertL new old (cdr lat)))))))

(define multirember
  (lambda (a lat)
    (cond ((null? lat) '())
          ((eq? a (car lat)) (multirember a (cdr lat)))
          (else
           (cons (car lat) (multirember a (cdr lat)))))))

(define multiinsertR
  (lambda (old new lat)
    (cond ((null? lat) '())
          ((eq? old (car lat)) (cons old (cons new (multiinsertR old new (cdr lat)))))
          (else
           (cons (car lat) (multiinsertR old new (cdr lat)))))))

(define multiinsertL
  (lambda (old new lat)
    (cond ((null? lat) '())
          ((eq? old (car lat)) (cons new (cons old (multiinsertL old new (cdr lat)))))
          (else
           (cons (car lat) (multiinsertL old new (cdr lat)))))))

(define multisubstr
  (lambda (old new lat)
    (cond ((null? lat) '())
          ((eq? old (car lat)) (cons new (multisubstr old new (cdr lat))))
          (else 
           (cons (car lat) (multisubstr old new (cdr lat)))))))

;m.itou added
(define multisubstrX
  (lambda (old new lat)
    (cond ((null? lat) '())
          ((not (atom? (car lat))) (cons (multisubstrX old new (car lat)) (multisubstrX old new (cdr lat))))
          ((eq? old (car lat)) (cons new (multisubstrX old new (cdr lat))))
          (else 
           (cons (car lat) (multisubstrX old new (cdr lat)))))))

(define pick
  (lambda (n lat)
    (cond ((null? lat) '()) 
          ((zero? (sub1 n)) (car lat))
          (else
           (pick (sub1 n) (cdr lat))))))

(define rempick
  (lambda (n lat)
    (cond ((null? lat) '())
          ((zero? (sub1 n)) (rempick  (sub1 n) (cdr lat)))
          (else
           (cons (car lat) (rempick (sub1 n) (cdr lat)))))))

(define no-nums
  (lambda (lat)
    (cond ((null? lat) '())
          ((number? (car lat)) (no-nums (cdr lat)))
          (else
           (cons (car lat) (no-nums (cdr lat)))))))

(define factorial (lambda (n m) (if (< n 1) m (factorial (- n 1) (* n m)))))

; 竹内関数
(define tak
  (lambda (x y z)
    (if (> x y)
        (tak (tak (- x 1) y z)
             (tak (- y 1) z x)
             (tak (- z 1) x y)) y)))

;fact-let
(define (fact-let n)
  (let loop((n1 n) (p n))
    (if (= n1 1)                    
        p
        (let ((m (- n1 1)))
          (loop m (* p m))))))
;eof
;(define *eof*
;  (let ((port (open-input-string "")))
;  (read port)))
