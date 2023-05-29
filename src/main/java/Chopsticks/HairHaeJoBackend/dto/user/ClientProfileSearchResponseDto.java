package Chopsticks.HairHaeJoBackend.dto.user;

import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientProfileSearchResponseDto {


    private int skinType;
    private int hairType;
    private int hairThickness;
    private int dyeingHistory;
    private int decolorizationHistory;
    private String abstractLocation;

    public static ClientProfileSearchResponseDto of(ClientProfile clientProfile) {
        return ClientProfileSearchResponseDto.builder()
            .skinType(clientProfile.getSkinType())
            .hairType(clientProfile.getHairType())
            .hairThickness(clientProfile.getHairThickness())
            .dyeingHistory(clientProfile.getDyeingHistory())
            .decolorizationHistory(clientProfile.getDecolorizationHistory())
            .abstractLocation(clientProfile.getAbstractLocation())
            .build();
    }
    }

