package ir.maktab.homeservice.entity;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode
public class ExpertUserId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "date")
    private LocalDate localDate;
}

