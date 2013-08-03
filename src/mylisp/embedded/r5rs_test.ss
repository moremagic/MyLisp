;; 単体テスト
(define test
    (lambda (fun)
        (if fun (display "OK") (display "NG"))))

;; 4.1.2 p8
(test (eq? (quote a)  a))
(test (eq? (quote #(a b c))  #(a b c)))
(test (eq? (quote (+ 1 2))  (+ 1 2)))

(test (eq? 'a  a))
(test (eq? '#(a b c)  #(a b c)))
(test (eq? '()  ()))
(test (eq? '(+ 1 2)  (+ 1 2)))
(test (eq? '(quote a)  (quote a)))
(test (eq? ''a  (quote a)))

;; 6-1 p17
(test (eq? (eqv? 'a 'a) #t))
(test (eq? (eqv? 'a 'b) #f))
(test (eq? (eqv? 2 2) #t))
(test (eq? (eqv? '() '()) #t))
(test (eq? (eqv? 100000000 100000000) #t))
(test (eq? (eqv? (cons 1 2) (cons 1 2)) #f))
(test (eq? (eqv? (lambda () 1) (lambda () 2)) #f))
(test (eq? (eqv? #f 'nil) #f))
(test (eq? (let ((p (lambda (x) x))) (eqv? p p)) #t))

;;  6-1 p18
(test (eq? (eq? 'a 'a) #t))
(test (eq? (eq? '(a) '(a)) #t));未規定
(test (eq? (eq? (list 'a) (list 'a)) #f))
(test (eq? (eq? "a" "a") #t)) ;未規定
(test (eq? (eq? "" "") #t)) ;未規定
(test (eq? (eq? '() '()) #t))
(test (eq? (eq? 2 2) #t)) ;未規定
(test (eq? (eq? #\A #\A) #t));未規定
(test (eq? (eq? car car) #t))
(test (eq? (let ((n (+ 2 3))) (eq? n n)) #t)) ;未規定
(test (eq? (let ((x '(a))) (eq? x x)) #t))
(test (eq? (let ((x '#())) (eq? x x)) #t))
(test (eq? (let ((p (lambda (x) x))) (eq? p p)) #t))