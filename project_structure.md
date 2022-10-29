# Software Development Technologies 2022. Project Structure.

[HOME](this_repo) | [COURSEWORK](coursework.md) | [PROJECT STRUCTURE](project_structure.md)

In order to make your work more organized next format is suggested and strongly asked to follow.

1. After cloning your fork of the discipline repository create a directory for your project:
    ```cmd
    git clone https://github.com/<your nickname>/SDT_2022.git
    cd SDT_2022
    cd IA-XX
    mkdir Surname_Name
    cd Surname_Name
    ```

2. Create required structure of your repository: 
    ```cmd
    mkdir src, documentation, reports, coursework
    ```
    Your project's directory content **MUST** follow next structure:
    ```
    Surname_Name
    |- src
    |- documentation
    |- reports
    |- coursework
    |- build.md
    ```
    - `src`. This directory is dedicated just for your project. Code, .gitignore, configs, etc.

    - `documentation`. Here you can put your UML diagrams, description of use-case scenarios, etc. Better to use **markdown** files from GitHub and declarative tool for UML (mermaid, or PlantUML). So in the end it will look like md files with description of your UML diagrams. For example, content of **documentation** directory may look as follows: 
        ```
        documentation
        |- use-cases.md
        |- sequence-auth_service-web_server.md
        |- sequence-validation_service-registration_service.md
        |- deployment.md
        |- components.md
        ```
        If you use graphic tools, which are not integrated to GitHub, publish only Readme.md with the list of links to source files of your diagrams (Visual Paradigm, draw.io, etc.). You can publish files beside the Readme.md, then put relative links to Readme.md.
    - `reports`. Contains directories with reports of your LABs and changelogs.

    - `coursework`. Directory for your production-ready project and publish-ready paper.

    - `build.md`. Description of build process. Must contain detailed instruction on how to build your project from the `src` folder.

3. For each LAB create a subdirectory in `reports` directory:
    ```cmd
    cd IA-XX\Surname_Name\reports
    mkdir YY
    cd YY
    echo "" > README.md
    ```
    In this case `YY` is lab number (02, ...). Publish your LAB reports in appropriate folders. Each lab should have its directory and contain report in **PDF** format and **Readme.md**, which contains a changelog. In simple words, it is just a description of what was changed in your `src` and `documentation`. Typical content of this directory **MUST** follow next structure:
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

Full script which you need to run to prepare required structure is next:
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

**ATTENTION**: in the script above replace placeholders `XX` and `Surname_Name` with your data!