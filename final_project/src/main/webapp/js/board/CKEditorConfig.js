//CDN 인포트한 내용중
//내용 사용할 메서드 설정
import {
  AccessibilityHelp,
  Autoformat,
  AutoImage,
  Autosave,
  BlockQuote,
  Bold,
  CloudServices,
  Essentials,
  Heading,
  ImageBlock,
  ImageInline,
  ImageInsert,
  ImageInsertViaUrl,
  ImageResize,
  ImageStyle,
  ImageTextAlternative,
  ImageToolbar,
  ImageUpload,
  Indent,
  IndentBlock,
  Italic,
  Link,
  Paragraph,
  SelectAll,
  SimpleUploadAdapter,
  TextTransformation,
  Underline,
  Undo,
} from 'CKEditor';

//한국어 설정 인포트
import translations from 'CKEditor/translations/ko.js';

export const editorConfig = {
  //인포트한값들중 메뉴에 보여줄 설정
  toolbar: {
	//메뉴바에 보여줄 메뉴를 설정해줍니다.
	//'|' <-- 해당 문자는 단지 메뉴를 나눠주기 위해 작성한 부분입니다.
    items: [
      'undo',
      'redo',
      '|',
      'heading',
      '|',
      'bold',
      'italic',
      'underline',
      '|',
      'link',
      'insertImage',
      'blockQuote',
      '|',
      'outdent',
      'indent',
    ],
    shouldNotGroupWhenFull: false,
  },
  //사용 플러그인 등록
  plugins: [
    AccessibilityHelp,
    Autoformat,
    AutoImage,
    Autosave,
    BlockQuote,
    Bold,
    CloudServices,
    Essentials,
    Heading,
    ImageBlock,
    ImageInline,
    ImageInsert,
    ImageInsertViaUrl,
    ImageResize,
    ImageStyle,
    ImageTextAlternative,
    ImageToolbar,
    ImageUpload,
    Indent,
    IndentBlock,
    Italic,
    Link,
    Paragraph,
    SelectAll,
    SimpleUploadAdapter,
    TextTransformation,
    Underline,
    Undo,
  ],
  //문단을 선택할 수 있게 설정
  heading: {
    options: [
      {
        model: 'paragraph',
        title: 'Paragraph',
        class: 'ck-heading_paragraph',
      },
      {
        model: 'heading1',
        view: 'h1',
        title: 'Heading 1',
        class: 'ck-heading_heading1',
      },
      {
        model: 'heading2',
        view: 'h2',
        title: 'Heading 2',
        class: 'ck-heading_heading2',
      },
      {
        model: 'heading3',
        view: 'h3',
        title: 'Heading 3',
        class: 'ck-heading_heading3',
      },
      {
        model: 'heading4',
        view: 'h4',
        title: 'Heading 4',
        class: 'ck-heading_heading4',
      },
      {
        model: 'heading5',
        view: 'h5',
        title: 'Heading 5',
        class: 'ck-heading_heading5',
      },
      {
        model: 'heading6',
        view: 'h6',
        title: 'Heading 6',
        class: 'ck-heading_heading6',
      },
    ],
  },
  //이미지 수정 설정
  image: {
	resizeOptions: [
	        {
	            name: 'resizeImage:original',
	            value: null,
	            icon: 'original'
	        },
	        {
	            name: 'resizeImage:custom',
	            value: 'custom',
	            icon: 'custom'
	        },
	        {
	            name: 'resizeImage:50',
	            value: '50',
	            icon: 'medium'
	        },
	        {
	            name: 'resizeImage:75',
	            value: '75',
	            icon: 'large'
	        }
	    ],
    toolbar: [
      'imageTextAlternative',
      '|',
      'imageStyle:inline',
      'imageStyle:wrapText',
      'imageStyle:breakText',
      '|',	  
	  'resizeImage:50',
	  'resizeImage:75'
    ],
  },
  // 파일 업로드 설정
  simpleUpload: {
	//파일을 저장할 서버의 주소를 입력
    uploadUrl: 'ckupload.do',
  },
  //초기 값 설정
  initialData: '',
  //언어 설정
  language: 'ko',
  //링크 설정
  link: {
    addTargetToExternalLinks: true,
    defaultProtocol: 'https://',
    decorators: {
      toggleDownloadable: {
        mode: 'manual',
        label: 'Downloadable',
        attributes: {
          download: 'file',
        },
      },
    },
  },
  //처음 사용자 UI/UX 개선을 위한 내용
  placeholder: '내용은 255자를 넘을 수 없습니다. \n',
  //사용자 언어 설정
  translations: [translations],
};


export function setContentsLength(str, imgMax, textMax) {
    var status = true;
    var results = str.match(/<img/g);
    var imgCnt = setImgLength(str);
    var textCnt = setTextLength(str);
    
    if(results != null) {
        $('.img-length').text(imgCnt+"장");
        if(imgCnt > imgMax) {
			//console.log("if(imgCnt > imgMax) { 실행 : " + imgCnt);
            status = false;
        }
    }
    
	console.log("if(textCnt > textMax) { 1: " + textCnt);
    if(textCnt > textMax) {
		//console.log("if(textCnt > textMax) { 2: " + textCnt);
		status = false;
    }
    
    if(!status) {
		//console.log("if(status) { : " + status);
        var msg = "등록오류\n글자수는 최대 "+textMax+"까지 등록이 가능합니다.\n현재 글자수 : "+textCnt+"자";
        if(obj.name == "ideaDetail3") {
            msg = "등록오류\n글자수는 최대 "+textMax+"자, 이미지는 "+imgMax+"장까지만 사용이 가능합니다.\n총 글자수 : "+textCnt+"자, 총 이미지수 : "+imgCnt+"장";
        }
		//console.log("if(status) { : " + msg);
		alert(msg);
    }
    
    //추후 form submit할때 체크할 데이터(true 일 경우 데이터 넘기지 않음-오류)
    return status;
}

export function setTextLength(str) {
	var textCnt = 0;
    var editorText = f_SkipTags_html(str);
    editorText = editorText.replace(/\s/gi,"");
    editorText = editorText.replace(/&nbsp;/gi, "");
    
	console.log("if(textCnt > textMax) { 1: " + editorText.length);
	textCnt = editorText.length;
	$('.text-length').text(textCnt+"자");
    
    //추후 form submit할때 체크할 데이터(true 일 경우 데이터 넘기지 않음-오류)
    return textCnt;
}

//이미지 개수를 구하는 함수
export function setImgLength(str) {
    var results = str.match(/<img/g);
    var imgCnt = 0;
    
    if(results != null) {
        imgCnt = results.length;
        $('.img-length').text(imgCnt+"장");
    }
    
    //추후 form submit할때 체크할 데이터(true 일 경우 데이터 넘기지 않음-오류)
    return imgCnt;
}

//태그제거용
function f_SkipTags_html(input, allowed) {
    allowed = (((allowed || "") + "").toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join(''); // making sure the allowed arg is a string containing only tags in lowercase (<a><b><c>)
    var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi,
    commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;
    return input.replace(commentsAndPhpTags, '').replace(tags, function ($0, $1) {
        return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
    });
}