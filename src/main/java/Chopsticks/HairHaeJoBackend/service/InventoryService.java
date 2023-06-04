package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.Inventory.MakeInventoryDto;
import Chopsticks.HairHaeJoBackend.entity.inventory.DesignerInventory;
import Chopsticks.HairHaeJoBackend.entity.inventory.DesignerInventoryRepository;
import Chopsticks.HairHaeJoBackend.entity.inventory.Item;
import Chopsticks.HairHaeJoBackend.entity.inventory.ItemRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final DesignerInventoryRepository designerInventoryRepository;
    private final ItemRepository itemRepository;
    private final S3UploadService s3UploadService;
    public void postInventory(MakeInventoryDto makeInventoryDto, MultipartFile image) throws IOException {
        String Photo=null;
        if(image!=null) Photo=s3UploadService.upload(image);
        try {
            Item item = itemRepository.save(makeInventoryDto.toItem(Photo));
            designerInventoryRepository.save(DesignerInventory.builder().userId(SecurityUtil.getCurrentMemberId()).itemId(item.getItemId()).build());
        }
        catch(Exception e) {
            throw new RuntimeException("인벤토리 작성 실패");
        }

    }
}
