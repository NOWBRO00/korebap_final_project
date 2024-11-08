package com.korebap.app.view.board;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korebap.app.biz.board.BoardDTO;
import com.korebap.app.biz.board.BoardService;
import com.korebap.app.biz.imageFile.ImageFileDTO;
import com.korebap.app.biz.imageFile.ImageFileService;
import com.korebap.app.view.common.LoginCheck;


@Controller
public class UpdateBoardController {

   @Autowired
   private BoardService boardService;

   @Autowired
   private LoginCheck loginCheck;

   @Autowired
   private ImageFileService imageFileService;

   // 사진 저장될 경로
   private final static String PATH = "PATH";


   // 게시글 수정 (페이지 이동)
   @GetMapping(value="/updateBoard.do")
   public String updateBoard(BoardDTO boardDTO, @RequestParam("board_num") int board_num, Model model,ImageFileDTO imageFileDTO) {
      // [ 게시글 수정]
      // 수정 페이지에 기존 데이터 보여주기 위해서 데이터 전달 필요
      // board + image
      System.out.println("************************************************************[com.korebap.app.view.board updateBoard(GET) 시작]************************************************************");

      System.out.println("*****com.korebap.app.view.board updateBoard(GET) 시작*****");

      // 경로를 담을 변수
      String viewName;

      // 상세페이지로 이동시키기 위해 변수 선언
//      int board_num = boardDTO.getBoard_num();

      // 로그인 체크
      String login_member_id = loginCheck.loginCheck();


      // 데이터 로그
      System.out.println("*****com.korebap.app.view.board updateBoard(GET) member_id 확인 : ["+login_member_id+"]*****");
      System.out.println("*****com.korebap.app.view.board updateBoard(GET) board_num 확인 : ["+board_num+"]*****");


      if(login_member_id.equals("")) { // 만약 로그인 상태가 아니라면 
         System.out.println("*****com.korebap.app.view.board updateBoard(GET) 로그인 세션 없음*****");

         // 로그인 안내 후 login 페이지로 이동시킨다
         model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
         model.addAttribute("path", "login.do");

         // 데이터를 보낼 경로
         viewName = "info";
      }
      else { // 로그인 상태라면

         System.out.println("*****com.korebap.app.view.board updateBoard(GET) 로그인 세션 있음*****");

         // [게시글 내용] 받기
         boardDTO.setBoard_condition("BOARD_SELECT_ONE");
         boardDTO = boardService.selectOne(boardDTO);

         System.out.println("*****com.korebap.app.view.board updateBoard(GET) boardDTO ["+boardDTO+"]*****");

         // [사진] 받기
         imageFileDTO.setFile_board_num(board_num);
         imageFileDTO.setFile_condition("BOARD_FILE_SELECTALL");
         List<ImageFileDTO> fileList = imageFileService.selectAll(imageFileDTO);

         System.out.println("*****com.korebap.app.view.board updateBoard(GET) fileList ["+fileList+"]*****");


         // updateBoard 페이지로 이동시킨다
         model.addAttribute("board", boardDTO);
         model.addAttribute("fileList", fileList);

         viewName ="updateBoard";

      }

      System.out.println("*****com.korebap.app.view.board updateBoard(GET) viewName ["+viewName+"]*****");

      System.out.println("************************************************************[com.korebap.app.view.board updateBoard(GET) 종료]************************************************************");

      return viewName;
   }



   // 게시글 수정
   @PostMapping(value="/updateBoard.do")
   public String updateBoard(BoardDTO boardDTO, Model model, ImageFileDTO imageFileDTO, RedirectAttributes redirectAttributes, List<MultipartFile> files) throws IllegalStateException, IOException {
      // [ 게시글 수정 ]

      System.out.println("************************************************************[com.korebap.app.view.board updateBoard(POST) 시작]************************************************************");


      // 경로를 담을 변수
      String viewName = "info";

      // 상세페이지로 이동시키기 위해 변수 선언
      int board_num = boardDTO.getBoard_num();

      // 로그인 체크 
      String member_id = loginCheck.loginCheck();

      // 데이터 로그
      System.out.println("*****com.korebap.app.view.board updateBoard(POST) member_id 확인 : ["+member_id+"]*****");
      System.out.println("*****com.korebap.app.view.board updateBoard(POST) board_num 확인 : ["+board_num+"]*****");
      System.out.println("*****com.korebap.app.view.board updateBoard(POST) boardDTO 확인 : ["+boardDTO+"]*****");


      if(member_id.equals("")) { // 만약 로그인 상태가 아니라면 
         System.out.println("*****com.korebap.app.view.board updateBoard(POST) updateBoard 로그인 세션 없음*****");

         // 로그인 안내 후 login 페이지로 이동시킨다
         model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
         model.addAttribute("path", "login.do");
      }
      else { // 로그인 상태라면

         System.out.println("*****com.korebap.app.view.board updateBoard(POST) updateBoard 로그인 세션 있음*****");


         // 데이터 로그

         System.out.println("*****com.korebap.app.view.board updateBoard(POST) board_title ["+boardDTO.getBoard_title()+"]*****");
         System.out.println("*****com.korebap.app.view.board updateBoard(POST) board_content ["+boardDTO.getBoard_content()+"]*****");
         System.out.println("*****com.korebap.app.view.board updateBoard(POST) board_dir ["+boardDTO.getBoard_file_dir()+"]*****");

         
         // [이미지 수정] 기존 이미지가 있는지 확인하기 위해 image 데이터를 받아온다.
         imageFileDTO.setFile_board_num(board_num);
         imageFileDTO.setFile_condition("BOARD_FILE_SELECTALL");
         List<ImageFileDTO> change_image = imageFileService.selectAll(imageFileDTO);


         System.out.println("*****com.korebap.app.view.board updateBoard(POST) change_image ["+change_image+"]*****");


         
         // 파일이 업로드되지 않았을 때 처리
         // 파일 리스트를 stream 타입으로 변환 (스트림 변환시 리스트 항목 연산 적용 가능)
         // MultipartFile::isEmpty는 모든 파일의 isEmpty() 메서드를 호출해, 각 파일이 비어 있는지 확인
         //      >> 실제로 업로드 되지 않거나 비어있는 경우 true 처리
         if (files == null || files.isEmpty() || 
               files.stream().allMatch(MultipartFile::isEmpty)) { 
            // 업로드된 파일이 없을 경우의 처리
            System.out.println("*****com.korebap.app.view.board updateBoard(POST) 업로드 된 사진 없음*****");
            // 필요에 따라 추가적인 로직을 작성
         }
         // 새 사진 저장 (업로드한 사진이 있다면)
         else if (!files.isEmpty() && files != null) {
            System.out.println("*****com.korebap.app.view.board updateBoard(POST) 사진 insert 시작*****");

            // 기존 이미지가 있는 경우, 기존 사진을 삭제한다
            if(change_image != null && !change_image.isEmpty()) {
               System.out.println("*****com.korebap.app.view.board updateBoard(POST) 기존 이미지가 있는 경우 *****");
               // list이기 때문에 for문을 돌려 삭제시킨다.
               for(ImageFileDTO imageDTO : change_image ) {
                  System.out.println("*****com.korebap.app.view.board updateBoard(POST) 기존 이미지 삭제를 위한 for문 *****");

                  // 기존 이미지 파일의 경로를 가져온다
                  String existingFileName = imageDTO.getFile_dir(); // 파일 경로 가져오기
                  File change_File = new File(PATH + existingFileName); // 변경될 파일 경로 설정
                  // exists 메서드를 통해 파일이 실제로 존재하는지 확인
                  if(change_File.exists()) { // 실제 존재한다면
                     change_File.delete(); // 기존 파일 삭제
                     boolean flag = imageFileService.delete(imageDTO); // DB에서도 삭제

                     if(!flag) { // DB 삭제 실패시
                        System.out.println("*****com.korebap.app.view.board updateBoard(POST) 사진 삭제 실패*****");

                        model.addAttribute("msg", "기존 파일 변경에 실패했습니다. 다시 시도해주세요.");
                        model.addAttribute("path", "updateBoard.do");
                        // 바로 이동
                        return viewName;
                     }
                  }

               }// 이미지 삭제 for문 종료
            } // 기존 이미지가 있는 경우 로직 종료


            // 반복문을 돌려 사진을 저장시킨다
            for (MultipartFile file : files) {

               String originalFilename = file.getOriginalFilename(); // 원본 파일명
               UUID uuid = UUID.randomUUID(); // 파일명 중복을 막기 위해 랜덤값 생성
               String imagePath = uuid.toString() + originalFilename;
               file.transferTo(new File(PATH + imagePath));

               // DB에 새 이미지 정보 저장
               imageFileDTO.setFile_dir(imagePath);
               imageFileDTO.setFile_product_num(board_num);
               imageFileDTO.setFile_condition("BOARD_FILE_INSERT");

               boolean imageFileUpdateFlag = imageFileService.insert(imageFileDTO);

               System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) imageFileUpdateFlag ["+imageFileUpdateFlag+"]*****");

               // 사진 inert 실패시
               if(!imageFileUpdateFlag) {
                  System.out.println("*****com.korebap.app.view.owner ownerProductUpdate(POST) 사진 변경(insert) 실패*****");

                  model.addAttribute("msg", "사진 등록에 실패했습니다. 다시 시도해주세요.");
                  model.addAttribute("path", "updateBoard.do");

                  // 바로 이동
                  return viewName;
               }
            }


         } // 새 사진 저장 로직 종료

         
         // Service에게 DTO 객체를 보내서 update 시킨다
         boolean flag = boardService.update(boardDTO);

         if(flag) { // 변경 성공했다면
            System.out.println("*****com.korebap.app.view.board updateBoard(POST) updateBoard 게시글 변경 성공*****");

            // 게시글 상세 페이지로 이동
            // 리다이렉트시 쿼리 매개변수를 자동으로 URL에 포함
            // 쿼리 매개변수 == URL에서 ? 기호 뒤에 위치하는 key-value 쌍
            redirectAttributes.addAttribute("board_num", board_num);

            viewName = "redirect:boardDetail.do";

         }
         else { // 변경 실패했다면 
            System.out.println("*****com.korebap.app.view.board updateBoard(POST) updateBoard 게시글 변경 실패*****");
            // 안내 + 게시글 상세 페이지로 이동

            model.addAttribute("msg", "게시글 수정에 실패했습니다. 다시 시도해주세요.");
            model.addAttribute("path", "boardDetail.do?board_num="+board_num);

            viewName = "info";
         }

      }

      // 이미지 업로드시 저장되는 시간이 소요되므로
      // Thread sleep를 걸어두어 페이지 이동 후 사진이 바로 나오도록 한다.
      try {
         Thread.sleep(5000);
         System.out.println("*****com.korebap.app.view.board updateBoard(POST) Thread.sleep(5000) 진행 중... *****");
         
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      
      System.out.println("*****com.korebap.app.view.board updateBoard(POST) viewName ["+viewName+"]*****");
      System.out.println("************************************************************[com.korebap.app.view.board updateBoard(POST) 종료]************************************************************");
      return viewName;
   }

}
