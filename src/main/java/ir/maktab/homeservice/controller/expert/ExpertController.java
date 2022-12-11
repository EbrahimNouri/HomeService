package ir.maktab.homeservice.controller.expert;

import ir.maktab.homeservice.entity.Expert;

import java.io.File;

public interface ExpertController {

    void registerExpert(Expert expert, File file);

    void acceptExpert(Expert expert);

    void changePassword(Expert expert, String password);
}
