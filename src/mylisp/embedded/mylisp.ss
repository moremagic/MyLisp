;;r5rs
(define add1 (lambda (n) (+ n 1)))

(define sub1 (lambda (n) (- n 1)))

(define atom?
  (lambda (x) 
    (and (not (pair? x)) (not (null? x)))))

(define < (lambda (n m) (> m n)))

(define  /
    (lambda (n m)
        (cond ((< n m) 0)
            (else (add1 (/ (- n m) m))))))
(define =
    (lambda (n m)
        (cond ((> n m) #f)
            ((< n m) #f)
            (else #t))))

(define zero?
    (lambda (x)
        (cond ((number? x) (= x 0))
            (else (eq? x 0)))))

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

(define length
    (lambda (L)
        (cond ((null? L) 0)
              (else (+ 1 (length (cdr L)))))))

(define (map f lst)
  (cond ((pair? lst)
         (cons (f (car lst)) (map f (cdr lst))))
        ((null? lst)
         '())
        (else
         (display "Not list"))))

(define lat?
  (lambda (n)
     (cond ((null? n) #t)
           ((atom? (car n)) (lat? (cdr n)))
           (else #f))))

(define member?
  (lambda (a lat)
    (cond ((null? lat) #f)
          ((eq? a (car lat)) #t)
          (else
           (member? a (cdr lat))))))


;;scheme 手習い




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
