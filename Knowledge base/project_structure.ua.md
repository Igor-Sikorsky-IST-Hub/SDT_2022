# Технології розроблення програмного забезпечення 2022. Структура проєкту.

[ГОЛОВНА][this_repo] | [КУРСОВА РОБОТА][coursework_readme] | [СТРУКТУРА ПРОЄКТУ][project_structure_readme]

З метою підвищення організації вашої роботи запроваджується наступний формат, якого слід строго дотримуватися.

1. Після клонування вашого форку репозиторію предмету створіть директорію для вашого проєкту:
    ```cmd
    git clone https://github.com/<your nickname>/SDT_2022.git
    cd SDT_2022
    cd IA-XX
    mkdir Surname_Name
    cd Surname_Name
    ```

2. Створіть наступну структуру директорій у вашому репозиторії:
    ```cmd
    mkdir src, documentation, reports, coursework
    ```
    Вміст директорії вашого репозиторію **ПОВИНЕН** дотримуватися наступної структури:
    ```
    Surname_Name
    |- src
    |- documentation
    |- reports
    |- coursework
    |- build.md
    ```
    - `src`. Ця директорія призначеня для виключно технічної складової вашого проєкту. Код, .gitignore, налаштування, файли стилів коду, тощо.

    - `documentation`. Тут будуть зберігатися ваші UML діагрми, описи сценаріїв прецедентів, тощо. Краще використовувати формат **markdown** файлів, які надає GitHub та декларативні інструменти для опису UML діаграм (mermaid, or PlantUML). Таким чином в результаті у вас будуть файли **md** (markdown) з описом ваших UML діаграм. Наприклад, типовий вміст папки **documentation** може бути наступним: 
        ```
        documentation
        |- use-cases.md
        |- sequence-auth_service-web_server.md
        |- sequence-validation_service-registration_service.md
        |- deployment.md
        |- components.md
        ```
        Якщо ви використовуєте графічні засоби для побудови діаграм, які не мають інтеграції з GitHub, опублікуйте Readme.md, який буде містити перелік посилань на вихідні файли діаграм (Visual Paradigm, draw.io, etc.). Також можете опублікувати вихідні файли прямо в цій папці і вставити відносне посилання в Readme.md.
    - `reports`. Ця папка повинна містити директорії зі звітами до лабораторних робіт та файл із описом змін, зроблених в роботі (changelogs).

    - `coursework`. Ця папка призначена для готового до релізу продукту і файл(и) курсової роботи.

    - `build.md`. Файл із описом процесу збирання. Повинен містити детальні інструкції по алгоритму збірки вашого проєкту з директорії `src`.

3. Для кожної лабораторної роботи створити підпапку в папці `reports`:
    ```cmd
    cd IA-XX\Surname_Name\reports
    mkdir YY
    cd YY
    echo "" > README.md
    ```
    У даному випадку `YY` позначає номер ЛР (02, ...). Публікуйте звіти по ЛР у відповідні папки. Кожна ЛР передбачає наявність окремої папки, у якій знаходиться звіт **виключно** у форматі **PDF** та файл **Readme.md**, який містить перелік або опис змін, які відбулися в `src` та/або `documentation`. Типовий вміст папки **ПОВИНЕН** відповідати наступній структурі:
    ```
    reports
    |- 02
        |- LR02.pdf
        |- Readme.md
    |- 03
        |- LR02.pdf
        |- Readme.md
    |- ...
    ```

Повний перелік команд (скрипт), який потрібно виконати, щоб отримати потрібну структуру папок:
```cmd
git clone https://github.com/<your nickname>/SDT_2022.git
cd SDT_2022
cd IA-XX
mkdir Surname_Name
cd Surname_Name
mkdir src, documentation, reports, coursework
cd reports
mkdir "02", "03", "04", "05", "06", "07", "08", "09" 
```

**УВАГА**: у скрипті вище замініть `XX` та `Surname_Name` вашими даними!

[this_repo]: <https://github.com/Igor-Sikorsky-IST-Hub/SDT_2022>
[coursework_readme]: <Knowledge base/coursework.md>
[project_structure_readme]: <Knowledge base/project_structure.md>
[coursework_readme_ua]: <Knowledge base/coursework.ua.md>
[project_structure_readme_ua]: <Knowledge base/project_structure.ua.md>