# Discover-Book

Discover Book არის Android აპლიკაცია, რომლის მეშვეობითაც შეგიძლიათ იპოვოთ თქვენთვის სასურველი წიგნი, წაიკითხოთ ან ნახოთ ინფორმაცია მის შესახებ.
აპლიკაცია ასევე საშუალებას გაძლევთ გაეცნოთ სხვების მოსაზრებებს ამა თუ იმ წიგნის ან მწერლის შესახებ. აგრეთვე, შესაძლებელია წიგნების ყიდვა-გაყიდვა(FAKE) 

აპლიკაციის გახსნისას უნდა გაიაროთ ერთჯერადად ავტორიზაცია მობილურის ნომრით ან Gmail-ით, რის შემდეგადაც თქვენ გექნებათ საშუალება შეხვიდეთ აპლიკაციის მთავარ გვერდზე. 
გამოყენებული ტექნოლოგია : Firebase Auth-UI
<img src="https://user-images.githubusercontent.com/44478420/87655235-0a81e580-c769-11ea-9eba-d079185c882a.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655235-0a81e580-c769-11ea-9eba-d079185c882a.png" width="200" height="400" />


მთავარ გვერდზე გადასვლისას, გადამისამართება მოხდება რეგისტრაციის გვერდზე, სადაც უნდა შეიყვანოთ თქვენი მონაცემები.
შენახვის ღილაკზე დაჭერის შემდეგ მონაცემები აიტვირთება Firebase Storage და Firebase Realtime Database-ში

<img src="https://user-images.githubusercontent.com/44478420/87655555-78c6a800-c769-11ea-96e2-efaba59bfae1.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655555-78c6a800-c769-11ea-96e2-efaba59bfae1.png" width="200" height="400" />

რეგისტრაციის დასრულების შემდეგ იხსნება მთავარი გვერდი, სადაც გამოსახულია კატეგორიების ჰორიზონტალური ReyclerView , მის ქვემოთ კი წიგნების ReyclerView , კატეგორიაზე დაჭერით იცვლება წიგნების ჩამონათვალი.
ქვემოთ კი არის პოსტების ჩამონათვალი

<img src="https://user-images.githubusercontent.com/44478420/87655634-9136c280-c769-11ea-9c95-2a8e60f45fc2.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655634-9136c280-c769-11ea-9c95-2a8e60f45fc2.png" width="200" height="400" />
<img src="https://user-images.githubusercontent.com/44478420/87655661-98f66700-c769-11ea-8fee-d9927c01b752.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655661-98f66700-c769-11ea-8fee-d9927c01b752.png" width="200" height="400" />

BottomNavigationView - ის მეორე Item -ზე დაჭერით გაიხსნება Store Fragment, სადაც განთავსებულია გასაყიდი წიგნების ჩამონათვალი.

<img src="https://user-images.githubusercontent.com/44478420/87655745-b0cdeb00-c769-11ea-9aff-98a0e1957be7.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655745-b0cdeb00-c769-11ea-9aff-98a0e1957be7.png" width="200" height="400" />
<img src="https://user-images.githubusercontent.com/44478420/87655753-b297ae80-c769-11ea-8198-db1a85f40aac.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655753-b297ae80-c769-11ea-8198-db1a85f40aac.png" width="200" height="400" />

მესამე Item-ზე დაჭერის შემდეგ იხსნება წიგნების ჩამონათვალი, რომელიც არის თქვენს მიერ შენახული და აქვს PDF კითხვის ფორმატი. Item გვიჩვენებს კითხვის პროგრესს.

<img src="https://user-images.githubusercontent.com/44478420/87655810-c17e6100-c769-11ea-8db6-0adc55b38135.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655810-c17e6100-c769-11ea-8db6-0adc55b38135.png" width="200" height="400" />
<img src="https://user-images.githubusercontent.com/44478420/87655816-c3482480-c769-11ea-8e52-40f3004b9e92.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655816-c3482480-c769-11ea-8e52-40f3004b9e92.png" width="200" height="400" />
<img src="https://user-images.githubusercontent.com/44478420/87655826-c511e800-c769-11ea-9116-1c0c6f207700.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655826-c511e800-c769-11ea-9116-1c0c6f207700.png" width="200" height="400" />

Profile Fragment - ში განთავსებულია იუზერის ინფორმაცია, ფავორიტი წიგნები და გასვლის ღილაკი.

<img src="https://user-images.githubusercontent.com/44478420/87655904-db1fa880-c769-11ea-857d-d45e69d3a743.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655904-db1fa880-c769-11ea-857d-d45e69d3a743.png" width="200" height="400" />

ძებნის ღილაკზე დაჭერით გადადის ძებნის ფრაგმენტზე, სადაც ძებნის ისტორია ინახება SharedPreference-ში.

<img src="https://user-images.githubusercontent.com/44478420/87655958-ea9ef180-c769-11ea-9c2c-0fe80ef8e457.png" data-canonical-src="https://user-images.githubusercontent.com/44478420/87655958-ea9ef180-c769-11ea-9c2c-0fe80ef8e457.png" width="200" height="400" />
