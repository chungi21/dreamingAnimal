package com.project.dreamingAnimal.specification;

import com.project.dreamingAnimal.entity.ProtectEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProtectSpecifications {

    public static Specification<ProtectEntity> hasSido(String sido) {
        return (root, query, builder) -> {
            if (sido == null || sido.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("sido"), sido);
        };
    }

    public static Specification<ProtectEntity> hasSigungu(String sigungu) {
        return (root, query, builder) -> {
            if (sigungu == null || sigungu.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("sigungu"), sigungu);
        };
    }

    public static Specification<ProtectEntity> hasByYear(String byYear) {
        return (root, query, builder) -> {
            if (byYear == null || byYear.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("byYear"), byYear);
        };
    }

    public static Specification<ProtectEntity> hasNum(String num) {
        return (root, query, builder) -> {
            if (num == null || num.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("num"), num);
        };
    }

    public static Specification<ProtectEntity> hasKind(String kind) {
        return (root, query, builder) -> {
            if (kind == null || kind.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("kind"), kind);
        };
    }

    public static Specification<ProtectEntity> hasIsProtect(Integer isProtect) {
        return (root, query, builder) -> {
            if (isProtect == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("isProtect"), isProtect);
        };
    }

    public static Specification<ProtectEntity> hasGender(String gender) {
        return (root, query, builder) -> {
            if (gender == null || gender.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("gender"), gender);
        };
    }

    public static Specification<ProtectEntity> hasDate(String date) {
        return (root, query, builder) -> {
            if (date == null || date.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("date"), date);
        };
    }
}
