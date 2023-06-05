package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.Inventory.ChangeInventoryDto;
import Chopsticks.HairHaeJoBackend.dto.Inventory.MakeInventoryDto;
import Chopsticks.HairHaeJoBackend.dto.Inventory.UseInventoryDto;
import Chopsticks.HairHaeJoBackend.entity.inventory.DesignerInventory;
import Chopsticks.HairHaeJoBackend.entity.inventory.DesignerInventoryRepository;
import Chopsticks.HairHaeJoBackend.entity.inventory.Item;
import Chopsticks.HairHaeJoBackend.entity.inventory.ItemRepository;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final DesignerInventoryRepository designerInventoryRepository;
    private final ItemRepository itemRepository;
    private final S3UploadService s3UploadService;
    private final UserRepository userRepository;
    public void postInventory(MakeInventoryDto makeInventoryDto, MultipartFile image) throws IOException {

        long currentId= SecurityUtil.getCurrentMemberId();
        isHairDesigner();
        String Photo=null;
        if(image!=null) Photo=s3UploadService.upload(image);
        try {
            Item item = itemRepository.save(makeInventoryDto.toItem(Photo));
            designerInventoryRepository.save(DesignerInventory.builder().userId(currentId).itemId(item.getItemId()).build());
        }
        catch(Exception e) {
            throw new RuntimeException("인벤토리 작성 실패");
        }


    }

    public boolean usestock(UseInventoryDto inventoryDto)  {
        long currentId= SecurityUtil.getCurrentMemberId();
        isHairDesigner();
        Item item=itemRepository.findById(inventoryDto.getItemId()).orElseThrow(() -> new RuntimeException("아이템 불러오기를 실패했습니다"));
        item.setStock(Math.max((item.getStock() - inventoryDto.getStock()), 0));
        itemRepository.save(item);
        return item.getStock()<=item.getWarningStock();






    }

    public boolean Change(MultipartFile itemImage,ChangeInventoryDto inventoryDto) throws IOException {
        isHairDesigner();
        Item item=itemRepository.findById(inventoryDto.getItemId()).orElseThrow(() -> new RuntimeException("아이템 불러오기를 실패했습니다"));
        item.setItemCategory(inventoryDto.getItemCategory());
        item.setItemName(inventoryDto.getItemName());
        String Photo=null;
        if(itemImage!=null)  {
            Photo=s3UploadService.upload(itemImage);
            item.setItemPhoto(Photo);
        }
        item.setItemPrice(inventoryDto.getItemPrice());
        item.setWarningStock(inventoryDto.getWarningStock());

        itemRepository.save(item);

        return item.getStock()<=item.getWarningStock();



    }

    public Collection<ChangeInventoryDto> View(String category, String name, boolean orderBystock, boolean orderByprice, boolean isWarning) throws IOException {
        isHairDesigner();

        return designerInventoryRepository.listfilter(category, name, orderBystock, orderByprice, isWarning,SecurityUtil.getCurrentMemberId());




    }

    public void delete(int itemId)  {
        long currentId= SecurityUtil.getCurrentMemberId();
        isHairDesigner();
        Item item=itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("아이템 불러오기를 실패했습니다"));
        DesignerInventory designerInventory=designerInventoryRepository.findByitemId(itemId);
        designerInventoryRepository.delete(designerInventory);
        itemRepository.delete(item);

    }
    private void isHairDesigner() {
        long currentId= SecurityUtil.getCurrentMemberId();
        User user=userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("로그인 상태가 아닙니다"));
        if(user.getRole() != Role.ROLE_DESIGNER) throw new RuntimeException("헤어디자이너만 접근 가능합니다");
    }
}
