package com.bradf83.springbootgenerator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;

/**
 * Created by bradf on 2016-09-16.
 */
@Controller
@RequestMapping("/")
public class GeneratorController {

    @GetMapping
    public String generator(@ModelAttribute GeneratorForm generatorForm){
        return "generator/generate";
    }

    @PostMapping(params = "addModelField")
    public String addModelField(@ModelAttribute GeneratorForm generatorForm){
        generatorForm.addModelField();
        return "generator/generate";
    }

    @PostMapping(params = "removeModelField")
    public String removeModelField(@ModelAttribute GeneratorForm generatorForm){
        generatorForm.removeModelField(generatorForm.getModelFieldToRemove());
        return "generator/generate";
    }

    @PostMapping
    public String generate(@ModelAttribute GeneratorForm generatorForm) throws IOException {

        // Create Directory
        File baseDir = new File("D:\\Examples\\First");
        boolean created = baseDir.mkdir();
        String capitalizedModelName = generatorForm.getModelName().substring(0, 1).toUpperCase() + generatorForm.getModelName().substring(1);
        String lowerCaseModelName = generatorForm.getModelName().substring(0, 1).toLowerCase() + generatorForm.getModelName().substring(1);

        if(created){
            File modelDir = new File("D:\\Examples\\First\\model");
            created = modelDir.mkdir();

            if(created){

                File modelFile = new File("D:\\Examples\\First\\model\\" + capitalizedModelName + ".java");
                created = modelFile.createNewFile();
                if(created) {
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(modelFile), "utf-8"))) {
                        writer.write("package " + generatorForm.getPackageName() + ".domain.models;");
                        writer.newLine();
                        writer.newLine();
                        // Imports
                        writer.write("import " + generatorForm.getPackageName() + ".domain.auditable." + generatorForm.getExtensionClass() + ";"); // TODO: Only if one is chosen
                        writer.newLine();
                        writer.write("import javax.persistence.*;");
                        writer.newLine();
                        writer.newLine();
                        //TODO: May need additional classes

                        // Comments

                        // Annotations
                        writer.write("@Table(name = \"" + generatorForm.getTableName() + "\")");
                        writer.newLine();
                        writer.write("@Entity");
                        writer.newLine();

                        // Class def
                        writer.write("public class " + capitalizedModelName + " extends " + generatorForm.getExtensionClass() + " {");
                        writer.newLine();
                        writer.newLine();

                        // Fields
                        for (ModelField modelField : generatorForm.getModelFields()) {
                            writer.write("\t@Column(name = \"" + modelField.getColumn() + "\")");
                            writer.newLine();
                            writer.write("\tprivate " + modelField.getType() + " " + modelField.getName() + ";");
                            writer.newLine();
                            writer.newLine();
                        }

                        // Empty Constructor
                        writer.write("\tpublic " + capitalizedModelName + "() {}");
                        writer.newLine();
                        writer.newLine();

                        // Getters and Setters
                        for (ModelField modelField : generatorForm.getModelFields()) {
                            String capitalizeName = modelField.getName().substring(0, 1).toUpperCase() + modelField.getName().substring(1);

                            // Getter
                            writer.write("\tpublic " + modelField.getType() + " get" + capitalizeName + "() { return " + modelField.getName() + "; }");
                            writer.newLine();
                            writer.newLine();

                            // Setter
                            writer.write("\tpublic void set" + capitalizeName + "(" + modelField.getType() + " " + modelField.getName() + ") { this." + modelField.getName() + " = " + modelField.getName() + "; }");
                            writer.newLine();
                            writer.newLine();
                        }

                        writer.write("}");
                    }
                }
            }
        }

        // Create the Repository Interface
        File repoDir = new File("D:\\Examples\\First\\repository");
        created = repoDir.mkdir();
        if(created){
            File repoFile = new File("D:\\Examples\\First\\repository\\" + capitalizedModelName + "Repository.java");
            created = repoFile.createNewFile();
            if(created){
                try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(repoFile), "utf-8"))){
                    writer.write("package " + generatorForm.getPackageName() + ".domain.repositories;");
                    writer.newLine();
                    writer.newLine();
                    // Imports
                    writer.write("import " + generatorForm.getPackageName() + ".domain.models." + capitalizedModelName + ";");
                    writer.newLine();
                    writer.write("import org.springframework.data.repository.CrudRepository;");
                    writer.newLine();
                    writer.write("import org.springframework.stereotype.Repository;");
                    writer.newLine();
                    writer.newLine();

                    //Comments

                    //Annotation
                    writer.write("@Repository");
                    writer.newLine();
                    writer.write("public interface " + capitalizedModelName + "Repository extends CrudRepository<" + capitalizedModelName +", Integer> {}");
                }
            }
        }

        // Create the Service Interface and Impl
        File serviceDir = new File("D:\\Examples\\First\\services");
        created = serviceDir.mkdir();
        if(created){
            File serviceFile = new File("D:\\Examples\\First\\services\\" + capitalizedModelName + "Service.java");
            created = serviceFile.createNewFile();
            if(created){
                // Interface
                try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFile), "utf-8"))){
                    writer.write("package " + generatorForm.getPackageName() + ".services;");
                    writer.newLine();
                    writer.newLine();

                    //Comments

                    writer.write("public interface " + capitalizedModelName + "Service {}");
                }
            }
            File serviceFileImpl = new File("D:\\Examples\\First\\services\\" + capitalizedModelName + "ServiceImpl.java");
            created = serviceFileImpl.createNewFile();
            if(created){
                // Interface
                try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFileImpl), "utf-8"))){
                    writer.write("package " + generatorForm.getPackageName() + ".services;");
                    writer.newLine();
                    writer.newLine();
                    // Imports
                    writer.write("import " + generatorForm.getPackageName() + ".domain.repositories." + capitalizedModelName + "Repository;");
                    writer.newLine();
                    writer.write("import org.springframework.stereotype.Service;");
                    writer.newLine();
                    writer.newLine();

                    // Comments

                    // Annotation
                    writer.write("@Service");
                    writer.newLine();
                    // Class Def
                    writer.write("public class " + capitalizedModelName + "ServiceImpl implements " + capitalizedModelName + "Service {");
                    writer.newLine();
                    writer.newLine();

                    // Dependencies
                    writer.write("\tprivate final " + capitalizedModelName + "Repository " + lowerCaseModelName + "Repository;");
                    writer.newLine();
                    writer.newLine();

                    // Constructor
                    writer.write("\tpublic " + capitalizedModelName + "ServiceImpl(" + capitalizedModelName + "Repository " + lowerCaseModelName + "Repository) {");
                    writer.newLine();
                    writer.write("\t\tthis." + lowerCaseModelName + "Repository = " + lowerCaseModelName + "Repository;");
                    writer.newLine();
                    writer.write("\t}");
                    writer.newLine();

                    writer.write("}");
                }
            }
        }

        // Create the Controller

        File controllerDir = new File("D:\\Examples\\First\\controllers");
        created = controllerDir.mkdir();
        if(created) {
            File controllerFile = new File("D:\\Examples\\First\\controllers\\" + capitalizedModelName + "Controller.java");
            created = controllerFile.createNewFile();
            if (created) {
                // Interface
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(controllerFile), "utf-8"))) {
                    writer.write("package " + generatorForm.getPackageName() + ".controllers;");
                    writer.newLine();
                    writer.newLine();
                    // Imports
                    writer.write("import " + generatorForm.getPackageName() + ".services." + capitalizedModelName + "Service;");
                    writer.newLine();
                    writer.write("import org.springframework.stereotype.Controller;");
                    writer.newLine();
                    writer.write("import org.springframework.ui.Model;");
                    writer.newLine();
                    writer.write("import org.springframework.validation.BindingResult;");
                    writer.newLine();
                    writer.write("import org.springframework.web.bind.WebDataBinder;");
                    writer.newLine();
                    writer.write("import org.springframework.web.bind.annotation.InitBinder;");
                    writer.newLine();
                    writer.write("import org.springframework.web.bind.annotation.ModelAttribute;");
                    writer.newLine();
                    writer.write("import org.springframework.web.bind.annotation.RequestMapping;");
                    writer.newLine();
                    writer.write("import org.springframework.web.bind.annotation.GetMapping;");
                    writer.newLine();
                    writer.write("import org.springframework.web.bind.annotation.PostMapping;");
                    writer.newLine();
                    writer.write("import javax.validation.Valid;");
                    writer.newLine();
                    writer.newLine();

                    //Comments

                    // Annotations
                    writer.write("@Controller");
                    writer.newLine();
                    writer.write("@RequestMapping(\"/" + lowerCaseModelName + "\")");
                    writer.newLine();

                    // Class Definition
                    writer.write("public class " + capitalizedModelName + "Controller {"); //TODO: extend a parent controller
                    writer.newLine();
                    writer.newLine();

                    //Dependencies
                    writer.write("\tprivate final " + capitalizedModelName + "Service " + lowerCaseModelName + "Service;");
                    writer.newLine();
                    writer.newLine();

                    // Constructor //TODO: Enhance with parent controller
                    writer.write("\tpublic " + capitalizedModelName + "Controller(" + capitalizedModelName + "Service " + lowerCaseModelName + "Service) {");
                    writer.newLine();
                    writer.write("\t\tthis." + lowerCaseModelName + "Service = " + lowerCaseModelName + "Service;");
                    writer.newLine();
                    writer.write("\t}");
                    writer.newLine();
                    writer.newLine();

                    // Init Binder
                    writer.write("\t@InitBinder");
                    writer.newLine();
                    writer.write("\tprotected void initBinder(WebDataBinder binder) {}");
                    writer.newLine();
                    writer.newLine();

                    // List
                    writer.write("\t@GetMapping");
                    writer.newLine();
                    writer.write("\tpublic String list() {return \"" + lowerCaseModelName + "/list\";}");
                    writer.newLine();
                    writer.newLine();

                    // Prepare Create
                    writer.write("\t@GetMapping(value = \"/create\")");
                    writer.newLine();
                    writer.write("\tpublic String create(@ModelAttribute " + capitalizedModelName + " " + lowerCaseModelName +") {return \"" + lowerCaseModelName + "/manage\";}");
                    writer.newLine();
                    writer.newLine();

                    // Save
                    writer.write("\t@PostMapping");
                    writer.newLine();
                    writer.write("\tpublic String save(@Valid @ModelAttribute " + capitalizedModelName + " " + lowerCaseModelName + ", BindingResult result) {");
                    writer.newLine();
                    writer.write("\t\tif(!result.hasErrors()) {");
                    writer.newLine();
                    writer.write("\t\t\t" + lowerCaseModelName + "Service.save(" + lowerCaseModelName + ");");
                    writer.newLine();
                    writer.write("\t\t\t// Do some logging, messaging or auditing");
                    writer.newLine();
                    writer.write("\t\t\treturn \"redirect:/" + lowerCaseModelName + "\";");
                    writer.newLine();
                    writer.write("\t\t}");
                    writer.newLine();
                    writer.write("\t\treturn \"" + lowerCaseModelName + "/manage\";");
                    writer.newLine();
                    writer.write("\t}");
                    writer.newLine();
                    writer.newLine();

                    //TODO: Edit adds the model attribute with a path variable, maybe a better way to do this.

                    // Prepare Edit
                    writer.write("\t@GetMapping(value = \"/{" + lowerCaseModelName + "}\")");
                    writer.newLine();
                    writer.write("\tpublic String edit(@PathVariable " + capitalizedModelName + " " + lowerCaseModelName + ", Model model) {");
                    writer.newLine();
                    writer.write("\t\tmodel.addAttribute(\"" + lowerCaseModelName + "\", " + lowerCaseModelName + ");");
                    writer.newLine();
                    writer.write("\t\treturn \"" + lowerCaseModelName + "/manage\";");
                    writer.newLine();
                    writer.write("\t}");
                    writer.newLine();
                    writer.newLine();

                    // Update
                    writer.write("\t@PostMapping(value = \"/{" + lowerCaseModelName + "}\")");
                    writer.newLine();
                    writer.write("\tpublic String update(@Valid @ModelAttribute " + capitalizedModelName + " " + lowerCaseModelName + ", BindingResult result) {");
                    writer.newLine();
                    writer.write("\t\tif(!result.hasErrors()) {");
                    writer.newLine();
                    writer.write("\t\t\t" + lowerCaseModelName + "Service.save(" + lowerCaseModelName + ");");
                    writer.newLine();
                    writer.write("\t\t\t// Do some logging, messaging or auditing");
                    writer.newLine();
                    writer.write("\t\t\treturn \"redirect:/" + lowerCaseModelName + "\";");
                    writer.newLine();
                    writer.write("\t\t}");
                    writer.newLine();
                    writer.write("\t\treturn \"" + lowerCaseModelName + "/manage\";");
                    writer.newLine();
                    writer.write("\t}");
                    writer.newLine();
                    writer.newLine();

                    // Delete
                    writer.write("\t@PostMapping(value = \"/{" + lowerCaseModelName + "}/delete\")");
                    writer.newLine();
                    writer.write("\tpublic String delete(@PathVariable " + capitalizedModelName + " " + lowerCaseModelName + ") {");
                    writer.newLine();
                    writer.write("\t\t" + lowerCaseModelName + "Service.delete(" + lowerCaseModelName + ");");
                    writer.newLine();
                    writer.write("\t\t// Do some logging, messaging or auditing");
                    writer.newLine();
                    writer.write("\t\treturn \"redirect:/" + lowerCaseModelName + "\";");
                    writer.newLine();
                    writer.write("\t}");
                    writer.newLine();

                    writer.write("}");
                }
            }
        }

        // Create basic HTML pages
        File templateDir = new File("D:\\Examples\\First\\templates");
        created = templateDir.mkdir();
        if(created) {
            File modelDir = new File("D:\\Examples\\First\\templates\\" + lowerCaseModelName + "\\");
            created = modelDir.mkdir();
            if (created) {
                File listFile = new File("D:\\Examples\\First\\templates\\" + lowerCaseModelName + "\\list.html");

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(listFile), "utf-8"))) {
                    this.writeBasicThymeleafHTMLPage(writer);
                }

                File manageFile = new File("D:\\Examples\\First\\templates\\" + lowerCaseModelName + "\\manage.html");
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(manageFile), "utf-8"))) {
                    this.writeBasicThymeleafHTMLPage(writer);
                }
            }

        }

        // Add generation success message

        return "generator/generate";
    }

    private void writeBasicThymeleafHTMLPage(BufferedWriter writer) throws IOException {
        writer.write("<!DOCTYPE html>");
        writer.newLine();
        writer.newLine();
        writer.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">");
        writer.newLine();
        writer.newLine();
        writer.write("<head><!-- Place Header Content Here --></head>");
        writer.newLine();
        writer.newLine();
        writer.write("<body><!-- Place Body Content Here --></body>");
        writer.newLine();
        writer.newLine();
        writer.write("</html>");
    }
}
