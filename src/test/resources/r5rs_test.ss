;; 単体テスト
(define test
    (lambda (fun ans)
        (cond
          ((equal? fun ans) (display "OK"))
          (else (display "NG")))))

;; 4.1.2 p8
(test (quote a)  'a)
(test (quote (+ 1 2))  '(+ 1 2))
(test 'a a)
(test '()  ())
(test '(+ 1 2)  (quote (+ 1 2)))
(test (quote (quote (a)))  ''(a))

;; 6-1 p17
;(test (eq? (eqv? 'a 'a) #t))
;(test (eq? (eqv? 'a 'b) #f))
;(test (eq? (eqv? 2 2) #t))
;(test (eq? (eqv? '() '()) #t))
;(test (eq? (eqv? 100000000 100000000) #t))
;(test (eq? (eqv? (cons 1 2) (cons 1 2)) #f))
;(test (eq? (eqv? (lambda () 1) (lambda () 2)) #f))
;(test (eq? (eqv? #f 'nil) #f))
;(test (eq? (let ((p (lambda (x) x))) (eqv? p p)) #t))

;;  6-1 p18
;;(test (eq? (eq? 'a 'a) #t))
;(test (eq? (eq? '(a) '(a)) #t));未規定
;(test (eq? (eq? (list 'a) (list 'a)) #f))
;(test (eq? (eq? "a" "a") #t)) ;未規定
;(test (eq? (eq? "" "") #t)) ;未規定
;(test (eq? (eq? '() '()) #t))
;(test (eq? (eq? 2 2) #t)) ;未規定
;(test (eq? (eq? #\A #\A) #t));未規定
;(test (eq? (eq? car car) #t))
;(test (eq? (let ((n (+ 2 3))) (eq? n n)) #t)) ;未規定
;(test (eq? (let ((x '(a))) (eq? x x)) #t))
;(test (eq? (let ((x '#())) (eq? x x)) #t))
;(test (eq? (let ((p (lambda (x) x))) (eq? p p)) #t))

;;  6-3 p25
;;(test (pair? '(a . b)) #t)
;;(test (pair? '(a b c)) #t)
;;(test (pair? '()) #f)
;;(test (pair? '#(a b)) #f)
;;-----------------------------------------------------------------------

; null? テスト
(test (null? ()) #t)
(test (null? '()) #t)
(test (null? 'a) #f)
(test (null? 123) #f)
(test (null? 1.23) #f)

;pair? テスト
(test (pair? '()) #f)
(test (pair? ()) #f)

; < テスト
(test (< 1 2) #t)
(test (< 1000 1000.1) #t)
(test (< 1000.2 1000.1) #f)
(test (< 1 1) #f)
(test (< 0.01 0.01) #f)

; zero? テスト
(test (zero? ()) #f)
(test (zero? 'atom) #f)
(test (zero? 0) #t)

; atom? テスト
(test (atom? ()) #f)
(test (atom? 'atom) #t)
(test (atom? 'turkey) #t)
(test (atom? 1492) #t)
(test (atom? 'u) #t)
(test (atom? *abc$) #t)
(test (atom? '(atom turkey or)) #f)
(test (atom? '(atom turkey or) or) #f)

;lat? テスト
(test (lat? '()) #t)
(test (lat? '(1 (3 4))) #f)
(test (lat? '(1 abc 4)) #t)

; insertR, insertL テスト
(test (insertR 'ddd 'ccc '(aaa bbb ccc eee fff)) '(aaa bbb ccc ddd eee fff))
(test (insertL 'ddd 'eee '(aaa bbb ccc eee fff)) '(aaa bbb ccc ddd eee fff))

;;rember
(test (rember and '(bacon lettuce and tomato)) '(bacon lettuce tomato))

; multirember テスト
(test (multirember 'aaa '(bbb aaa vvv ccc aaa bbb)) '(bbb vvv ccc bbb))

; multiinsertR, multiinsertL テスト
(test (multiinsertR 'aaa 'bbb '(aaa ccc dddd eee aaa ccc)) '(aaa bbb ccc dddd eee aaa bbb ccc))
(test (multiinsertL 'bbb 'aaa '(bbb ccc ddd eee bbb ccc)) '(aaa bbb ccc ddd eee aaa bbb ccc))

; multiinsertR, multiinsertL テスト
(test (multisubstr '*** 'aaa '(*** bbb ccc (*** bbb ccc))) '(aaa bbb ccc (*** bbb ccc)))

; member? テスト
(test (member? 'ccc '(bbaab ccc (aaa bbb rrr) bbb)) #t)
(test (member? 'aaa '(bbaab ccc (aaa bbb rrr) bbb)) #f)

; pick テスト
(test (pick 4 '(aa bb cc ddd ee ff)) 'ddd)
(test (pick 1 '(aa bb cc ddd ee ff)) 'aa)
(test (pick 0 '(aa bb cc ddd ee ff)) '())

; rempick テスト
(test (rempick 1 '(a b c d e)) '(b c d e))
(test (rempick 3 '(a b c d e)) '(a b d e))

; no-nums テスト
(test (no-nums '(1 2 3 h a c 4 k 5 e 6 r)) '(h a c k e r))

;;#1 p139 multirember&co テスト
(define a-friend
  (lambda (x y)
    (length x)))
(define b-friend
  (lambda (x y)
    (length y)))

(test (multirember&co 'tuna '(tuna aaa bbb tuna ccc ddd) a-friend) '4)
(test (multirember&co 'tuna '(tuna aaa bbb tuna ccc ddd) b-friend) '2)

; factorial テスト
;(test (factorial 1000 1)
;        402387260077093773543702433923003985719374864210714632543799910429938512398629020592044208486969404800479988610197196058631666872994808558901323829669944590997424504087073759918823627727188732519779505950995276120874975462497043601418278094646496291056393887437886487337119181045825783647849977012476632889835955735432513185323958463075557409114262417474349347553428646576611667797396668820291207379143853719588249808126867838374559731746136085379534524221586593201928090878297308431392844403281231558611036976801357304216168747609675871348312025478589320767169132448426236131412508780208000261683151027341827977704784635868170164365024153691398281264810213092761244896359928705114964975419909342221566832572080821333186116811553615836546984046708975602900950537616475847728421889679646244945160765353408198901385442487984959953319101723355556602139450399736280750137837615307127761926849034352625200015888535147331611702103968175921510907788019393178114194545257223865541461062892187960223838971476088506276862967146674697562911234082439208160153780889893964518263243671616762179168909779911903754031274622289988005195444414282012187361745992642956581746628302955570299024324153181617210465832036786906117260158783520751516284225540265170483304226143974286933061690897968482590125458327168226458066526769958652682272807075781391858178889652208164348344825993266043367660176999612831860788386150279465955131156552036093988180612138558600301435694527224206344631797460594682573103790084024432438465657245014402821885252470935190620929023136493273497565513958720559654228749774011413346962715422845862377387538230483865688976461927383814900140767310446640259899490222221765904339901886018566526485061799702356193897017860040811889729918311021171229845901641921068884387121855646124960798722908519296819372388642614839657382291123125024186649353143970137428531926649875337218940694281434118520158014123344828015051399694290153483077644569099073152433278288269864602789864321139083506217095002597389863554277196742822248757586765752344220207573630569498825087968928162753848863396909959826280956121450994871701244516461260379029309120889086942028510640182154399457156805941872748998094254742173582401063677404595741785160829230135358081840096996372524230560855903700624271243416909004153690105933983835777939410970027753472000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000)

; Y テスト
(test ((eq?-c 'salada) 'salada) #t)

; 竹内関数テスト
(test (tak 3 2 1) 3)

(define (fib n)
  (if (< n 2)
      n
      (+ (fib (- n 1))
         (fib (- n 2)))))

(define (make-bank-account amount)
  (lambda (n)
    (set! amount (+ amount n))
    amount))
(define yamada-bank-account (make-bank-account 1000))
(define tanaka-bank-account (make-bank-account 1000))
(test (yamada-bank-account -5000) -4000)
(test (tanaka-bank-account 100) 1100)

; let テスト ;=> 5
(test (let ((x 2))
  (let ((y 3))
        (+ x y))) 5)

;ファイル読み込みテスト
;http://www.geocities.jp/m_hiroi/func/abcscm07.html
(define (type1 filename)
    (let ((iport (open-input-file filename)))
        (let loop ((c (read-char iport)))
	    (cond ((not (eof-object? c))
	           (display c)
		   (loop (read-char iport)))))
        (close-input-port iport)))

;;(type1 "C:\Users\mitsu\Desktop\mylisp\test.txt")
;;(type1 "C:\Users\mitsu\Desktop\mylisp\mylisp.lisp.ss")

(time (fib 30));;34641ms

;;let set! の 変数スコープの確認
;;バグのため未稼働
;;(define (test)
;;    (let ((*big* 100))
;;        (define (set-sample) (set! *big* (+ *big* 1)))
;;        (define (let-sample) (let ((*big* *big*)) (set! *big* (+ *big* 1))))
;;        (set-sample)
;;        (display *big*)
;;        (let-sample)
;;        (let-sample)
;;        (display *big*)
;;        ))
;;(test)
