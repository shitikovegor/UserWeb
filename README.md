# Описание проекта Help BY Car.

Автор: Шитиков Егор
***
**Цель проекта** – создание системы взаимодействия людей, которым нужна помощь, связанная с перевозкой, и людей, которые готовы оказать такую помощь. 

В данном проекте пользователю предоставлена возможность:

-	зарегистрироваться как водителю-волонтеру, выбирать заявки и оказывать по ним помощь;
-	Зарегистрироваться как клиенту, размещать заявки на оказание помощи.
***

### Пользователи
В приложении используются следующие роли:
1.	Гость
2.	Клиент
3.	Водитель
4.	Администратор

#### Гость
Неавторизированный пользователь.

Функциональные возможности:
-	Просмотр домашней страницы;
-	Просмотр заявок;
-	Поиск заявок по критериям;
-	Смена локализации;
-	Авторизация/Регистрация;
-	Активация аккаунта по ссылке, отправленной по почте.

#### Клиент
Клиент может быть заблокирован администратором. До активации аккаунта находится в статусе «Не активирован» - не может осуществить вход на сайт до перехода по ссылке активации. Активация осуществляется по переходу по ссылке, которая отправляется на электронный ящик пользователя при успешной регистрации.

Функциональные возможности:
-	Просмотр домашней страницы;
-	Просмотр заявок;
-	Поиск заявок по критериям;
-	Добавление заявки;
-	Редактирование заявки;
-	Удаление/Отмена заявки;
-	Выставление отметки выполнения заявки;
-	Редактирование данных аккаунта;
-	Редактирование контактных данных;
-	Смена пароля;
-	Смена локализации;
-	Выход из аккаунта.

#### Водитель
Водитель может быть заблокирован администратором. До активации аккаунта находится в статусе «Не активирован» - не может осуществить вход на сайт до перехода по ссылке активации. Активация осуществляется по переходу по ссылке, которая отправляется на электронный ящик пользователя при успешной регистрации.

Функциональные возможности:
-	Просмотр домашней страницы;
-	Просмотр заявок;
-	Поиск заявок по критериям;
-	Отмена выполнения заявки;
-	Добавление автомобиля;
-	Редактирование автомобиля;
-	Удаление автомобиля;
-	Редактирование данных аккаунта;
-	Редактирование контактных данных;
-	Смена пароля;
-	Смена локализации;
-	Выход из аккаунта.

#### Администратор
Осуществляет контроль за действиями пользователей, редактирует данные. Удаляет некорректные данные. Имеет возможность блокировки пользователей.

Функциональные возможности:
-	Просмотр домашней страницы;
-	Просмотр заявок;
-	Поиск заявок по критериям;
-	Просмотр пользователей;
-	Просмотр заявок/автомобилей пользователей;
-	Редактирование заявки;
-	Удаление/Отмена заявки;
-	Отмена выполнения заявки;
-	Редактирование автомобиля;
-	Удаление автомобиля;
-	Блокировка/Разблокировка пользователя;
-	Смена локализации;
-	Выход из аккаунта.
***

### Предметная область
**Заявки на помощь** создаются клиентами, принимаются в работу водителями.

При создании заявки необходимо указать следующие данные:
-	Заголовок;
-	Тип заявки (Груз/Пассажиры);
-	Количественные показатели в зависимости от типа (вес и объем / количество пассажиров);
-	Дата отправления;
-	Адрес отправления;
-	Дата прибытия;
-	Адрес прибытия;
-	Описание.

После создания заявке присваивается статус «Активен». При принятии заявки в работу водителем присваивается статус «Подтвержден». После выполнения заявки присваивается статус «Выполнен».

Заявка в статусе «Активный» может быть удалена. После принятия заявки в работу ее можно отменить. Будет присвоен статус «Отменен».

Для корректной работы с заявками водители добавляют в систему **данные своих автомобилей**. 

При добавлении автомобиля необходимо указать следующие данные:
-	Номер автомобиля;
-	Грузоподъемность – вес;
-	Грузоподъемность – объем;
-	Максимальное количество пассажиров.

При принятии в работу заявки система сверяет данные автомобиля и данные заявки. Если грузовые / пассажирские данные заявки превышают данные автомобиля, заявка не может быть принята.

Пользователь может редактировать, а также удалить автомобиль.



******



# Project description. Help BY Car

Author: Shitikov Egor
***
**The goal of the project**is to create a system of interaction between people who need assistance related to transportation and people who are ready to provide such assistance.

In this project, the user is given the opportunity to:

-	Register as a volunteer driver, select applications and provide assistance on them;
-	Register as a client, add applications for assistance.
***

### Users
The project uses the following roles:
1.	Guest
2.	Client
3.	Driver
4.	Administrator

#### Guest
Unautorized user.

Functionality:
- View the home page;
- View applications;
- Search for applications by criteria;
-	Change of localization;
-	Authorization / Registration;
-	Activation of the account by the link sent by mail.

#### Client
The client can be blocked by the administrator. Before the activation of the account, it is in the "Not activated" status - he cannot enter the site before clicking on the activation link. Activation comes by clicking on the link, which is sent to the user's email upon successful registration.

Functionality:
- View the home page;
-	View applications;
-	Search for applications by criteria;
-	Add an application;
-	Edit the application;
-	Delete / Cancel an application;
-	Place a check mark of the application;
-	Edit account data;
-	Edit contact information;
-	Change password;
-	Change of localization;
-	Log out.

#### Driver
Driver can be blocked by the administrator. Before the activation of the account, it is in the "Not activated" status - he cannot enter the site before clicking on the activation link. Activation comes by clicking on the link, which is sent to the user's email upon successful registration.

Functionality:
-	View the home page;
-	View applications;
-	Search for applications by criteria;
-	Cancel an application;
-	Add a car;
-	Edit the car;
-	Delete the car;
-	Edit account data;
-	Edit contact information;
-	Change password;
-	Change of localization;
-	Log out.

#### Administartor
Carries out control over user actions, edits data. Deletes invalid data. Has the ability to block users.

Functionality:
-	View the home page;
-	View applications;
-	Search for applications by criteria;
-	View users;
-	View user’s applications/cars;
-	Edit the application;
-	Delete / Cancel an application;
-	Cancel an application;
-	Edit the car;
-	Delete the car;
-	Block/unblock the user;
-	Change of localization;
-	Log out.
***

### Subject area
Clients add **assistance applications**, drivers confirm them.

When creating an application, you must specify the following data:
-	Title;
-	Type of application (Cargo/Passengers);
-	Indicators depending on the type (weight and volume / number of passengers);
-	Departure date;
-	Departure address;
-	Arrival date;
-	Arrival address;
-	Description.

After creation, the application assigns the status "Active". When the driver accepts an application for work, the application assigns the status "Confirmed". After the application has been completed, it assigns the status “Completed”.

A application in the "Active" status can be deleted. After accepting an application for work, it can be canceled. The status "Canceled" will be assigned.

For the correct work with applications, drivers add **the data of their cars** to the system. 

When adding a car, you must specify the following data:
-	Car number;
-	Carrying capacity - weight;
-	Carrying capacity - volume;
-	Maximum number of passengers.

When accepting an application, the system verifies the vehicle data and the application data. If the cargo / passenger data of the application exceeds the vehicle data, the application cannot be accepted.

The user can edit as well as delete the car.
