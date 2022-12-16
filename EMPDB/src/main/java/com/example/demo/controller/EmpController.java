package com.example.demo.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Employee;
import com.example.demo.entity.csv;
import com.example.demo.entity.csvExport;
import com.example.demo.entity.PullDown.Branch;
import com.example.demo.entity.PullDown.BusinessOrgnization;
import com.example.demo.entity.PullDown.Company;
import com.example.demo.entity.PullDown.Division;
import com.example.demo.entity.PullDown.EmpJob;
import com.example.demo.entity.PullDown.GeneralBranch;
import com.example.demo.service.EmpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Controller
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//ラジオボタンのMap作成
	private Map<Boolean, String> radioButton;
	
	//初期化メソッドを作成し、ラジオボタンを格納
	private Map<Boolean, String> initRadioButton() {
		Map<Boolean, String> radio = new LinkedHashMap<>();
		radio.put(true, "メイン所属");
		radio.put(false, "兼務");
		
		return radio;
	}
	
	//従業員一覧画面の表示
	@GetMapping("/")
	public String index(Model model, @PageableDefault(page=0, size = 100) Pageable pageable) {
		//従業員一覧の生成
		Page<Employee> page = empService.selectAll(pageable);
		
		//modelに従業員リストを登録
		model.addAttribute("page", page);
	    model.addAttribute("pageList", page.getContent());
		//model.addAttribute("employeeList", employeeList);
		
		return "index";
	}
	
	//従業員情報詳細画面の表示
	@GetMapping("/employeeDetail/{id}")
	public String getEmployeeDetail(Model model,@PathVariable("id") int id) {
		//従業員情報を取得
		Employee employee = empService.selectOne(id);
		model.addAttribute("employee", employee);
		
		return "employeeDetail";
	}
	
	//従業員登録画面のGETメソッド
	@GetMapping("/create")
	public String getCreate(Model model) {
		//バリデーションエラーが出た場合データを保持した状態で表示する
		if (!model.containsAttribute("employee")) {
	        model.addAttribute("employee", new Employee());
		}
		
		//事業本部のプルダウンを表示する
		List<BusinessOrgnization> bus_org = empService.getBusinessOrgnizationAll();
		model.addAttribute("bus_org", bus_org);
		
		//事業部のプルダウンリスト表示
		List<Division> division = empService.getDivisionAll();
		model.addAttribute("division", division);
		
		//法人のプルダウンリスト表示
		List<Company> company = empService.getCompanyAll();
		model.addAttribute("company", company);
		
		//拠点統括のプルダウンリスト表示
		List<GeneralBranch> genBra = empService.getGenBraAll();
		model.addAttribute("genBra", genBra);
		
		//拠点のプルダウンリスト表示
		List<Branch> branch = empService.getBranchAll();
		model.addAttribute("branch", branch);

		//職務のプルダウンリスト表示
		List<EmpJob> empJob = empService.getEmpJobAll();
		model.addAttribute("empJob", empJob);
		
		//メイン所属・兼務のラジオボタン表示
		radioButton = initRadioButton();
		model.addAttribute("radioButton", radioButton);

		//雇用体系のプルダウン表示
		Map<String, String> employmentType= new LinkedHashMap<String, String>();
		employmentType.put("正社員","正社員");
		employmentType.put("役員","役員");
		employmentType.put("ﾊﾟｰﾄ･ｱﾙﾊﾞｲﾄ","ﾊﾟｰﾄ･ｱﾙﾊﾞｲﾄ");
		employmentType.put("業務委託","業務委託");
		employmentType.put("契約社員","契約社員");
		employmentType.put("派遣","派遣");
		employmentType.put("嘱託","嘱託");
		model.addAttribute("employmentType", employmentType);
		
		//登録画面に遷移
		return "create";
	}
	
	//従業員登録のPOSTメソッド
	@PostMapping("/postCreate")
	public String postCreate(@Validated Employee employee, BindingResult bindingResult, Model model, RedirectAttributes attributes) {	
		//バリデーション時に入力内容を保持しつつリダイレクト
		if (bindingResult.hasErrors()) {
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", bindingResult);
            attributes.addFlashAttribute("employee", employee);
			
			return "redirect:/create";
        }
		
		//DBに従業員登録を行う
		empService.insertOne(employee);
		
		//一覧画面にリダイレクト（登録完了画面あればいい）
		return "redirect:/";
	}
	
	//編集画面の表示のGETメソッド
	@GetMapping("/employeeDetail/{id}/edit")
	public String displayEdit(@PathVariable("id") int id, Model model) {
		//バリデーションエラーが出た場合データを保持した状態で表示する
		if (!model.containsAttribute("employee")) {
			Employee employee = empService.selectOne(id);
			model.addAttribute("employee", employee);
	    }
		
		//事業本部のプルダウンを表示する
		List<BusinessOrgnization> bus_org = empService.getBusinessOrgnizationAll();
		model.addAttribute("bus_org", bus_org);
		
		//事業部のプルダウンリスト表示
		List<Division> division = empService.getDivisionAll();
		model.addAttribute("division", division);
		
		//法人のプルダウンリスト表示
		List<Company> company = empService.getCompanyAll();
		model.addAttribute("company", company);
		
		//拠点統括のプルダウンリスト表示
		List<GeneralBranch> genBra = empService.getGenBraAll();
		model.addAttribute("genBra", genBra);
		
		//拠点のプルダウンリスト表示
		List<Branch> branch = empService.getBranchAll();
		model.addAttribute("branch", branch);

		//拠点のプルダウンリスト表示
		List<EmpJob> empJob = empService.getEmpJobAll();
		model.addAttribute("empJob", empJob);
		
		//メイン所属・兼務のラジオボタン表示
		radioButton = initRadioButton();
		model.addAttribute("radioButton", radioButton);
		
		//雇用体系のプルダウン表示
		Map<String, String> employmentType= new LinkedHashMap<String, String>();
		employmentType.put("正社員","正社員");
		employmentType.put("役員","役員");
		employmentType.put("ﾊﾟｰﾄ･ｱﾙﾊﾞｲﾄ","ﾊﾟｰﾄ･ｱﾙﾊﾞｲﾄ");
		employmentType.put("業務委託","業務委託");
		employmentType.put("契約社員","契約社員");
		employmentType.put("派遣","派遣");
		employmentType.put("嘱託","嘱託");
		model.addAttribute("employmentType", employmentType);
		
		//在職状況のプルダウン表示
		Map<String, String> status = new LinkedHashMap<String, String>();
		status.put("在職","在職");
		status.put("休職中","休職中");
		status.put("退職","退職");
		model.addAttribute("status", status);
		
		return "edit";
	}
	
	//更新処理のPOSTメソッド
	@PostMapping("/employeeDetail/{id}/update")
	public String postUpdate(@Validated Employee employee,BindingResult bindingResult,RedirectAttributes attributes,Model model,@PathVariable("id") int id) {
		if (bindingResult.hasErrors()) {
			//従業員情報をidから取得
			employee = empService.selectOne(id);
			
			//リダイレクト先にパラメータを渡す	
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", bindingResult);
            attributes.addFlashAttribute("employee", employee);
            
			//事業本部のプルダウンを表示する
			List<BusinessOrgnization> bus_org = empService.getBusinessOrgnizationAll();
			attributes.addFlashAttribute("bus_org", bus_org);
			
			//事業部のプルダウンリスト表示
			List<Division> division = empService.getDivisionAll();
			attributes.addFlashAttribute("division", division);
			
			//法人のプルダウンリスト表示
			List<Company> company = empService.getCompanyAll();
			attributes.addFlashAttribute("company", company);
			
			//拠点統括のプルダウンリスト表示
			List<GeneralBranch> genBra = empService.getGenBraAll();
			attributes.addFlashAttribute("genBra", genBra);
			
			//拠点のプルダウンリスト表示
			List<Branch> branch = empService.getBranchAll();
			attributes.addFlashAttribute("branch", branch);

			//拠点のプルダウンリスト表示
			List<EmpJob> empJob = empService.getEmpJobAll();
			attributes.addFlashAttribute("empJob", empJob);
			
			//メイン所属・兼務のラジオボタン表示
			radioButton = initRadioButton();
			attributes.addFlashAttribute("radioButton", radioButton);
			
			//雇用体系のプルダウン表示
			Map<String, String> employmentType= new LinkedHashMap<String, String>();
			employmentType.put("正社員","正社員");
			employmentType.put("役員","役員");
			employmentType.put("ﾊﾟｰﾄ･ｱﾙﾊﾞｲﾄ","ﾊﾟｰﾄ･ｱﾙﾊﾞｲﾄ");
			employmentType.put("業務委託","業務委託");
			employmentType.put("契約社員","契約社員");
			employmentType.put("派遣","派遣");
			employmentType.put("嘱託","嘱託");
			attributes.addFlashAttribute("employmentType", employmentType);
			
			//在職状況のプルダウン表示
			Map<String, String> status = new LinkedHashMap<String, String>();
			status.put("在職","在職");
			status.put("休職中","休職中");
			status.put("退職","退職");
			model.addAttribute("status", status);
			attributes.addFlashAttribute("status", status);
			
            return "redirect:/employeeDetail/{id}/edit";
        }
		
		empService.editOne(employee,id);
		
		return "redirect:/employeeDetail/{id}";
	}
	
	//兼務情報入力のGETメソッド
	@GetMapping("/employeeDetail/{id}/sub")
	public String displaySub(@PathVariable("id") int id, Model model) {
		//バリデーションエラーが出た場合データを保持した状態で表示する
		if (!model.containsAttribute("employee")) {
			Employee employee = empService.selectOne(id);
			model.addAttribute("employee", employee);
	    }
		
		//事業本部のプルダウンを表示する
		List<BusinessOrgnization> bus_org = empService.getBusinessOrgnizationAll();
		model.addAttribute("bus_org", bus_org);
		
		//事業部のプルダウンリスト表示
		List<Division> division = empService.getDivisionAll();
		model.addAttribute("division", division);
		
		//法人のプルダウンリスト表示
		List<Company> company = empService.getCompanyAll();
		model.addAttribute("company", company);
		
		//拠点統括のプルダウンリスト表示
		List<GeneralBranch> genBra = empService.getGenBraAll();
		model.addAttribute("genBra", genBra);
		
		//拠点のプルダウンリスト表示
		List<Branch> branch = empService.getBranchAll();
		model.addAttribute("branch", branch);

		//拠点のプルダウンリスト表示
		List<EmpJob> empJob = empService.getEmpJobAll();
		model.addAttribute("empJob", empJob);
		
		//メイン所属・兼務のラジオボタン表示
		radioButton = initRadioButton();
		model.addAttribute("radioButton", radioButton);
		
		//雇用体系のプルダウン表示
		Map<String, String> employmentType= new LinkedHashMap<String, String>();
		employmentType.put("正社員","正社員");
		employmentType.put("役員","役員");
		employmentType.put("ﾊﾟｰﾄ･ｱﾙﾊﾞｲﾄ","ﾊﾟｰﾄ･ｱﾙﾊﾞｲﾄ");
		employmentType.put("業務委託","業務委託");
		employmentType.put("契約社員","契約社員");
		employmentType.put("派遣","派遣");
		employmentType.put("嘱託","嘱託");
		model.addAttribute("employmentType", employmentType);
		
		//在職状況のプルダウン表示
		Map<String, String> status = new LinkedHashMap<String, String>();
		status.put("在職","在職");
		status.put("休職中","休職中");
		status.put("退職","退職");
		model.addAttribute("status", status);
		
		return "sub";
	}
	
	//兼務情報を登録のPOSTメソッド
	@PostMapping("/employeeDetail/{id}/sub")
	public String postSub(Employee employee, @PathVariable("id") int id, Model model) {
		
		int sub_id = empService.insertSub(employee, id);
		
		return "redirect:/employeeDetail/" + sub_id;
	}
	
	//日付がNullの場合にエラーが出ないようにする処理
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);

	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@GetMapping("/searchEmp")
	public String search() {
		return "search";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String empName, @RequestParam String empName_kana, @RequestParam String companyName, Model model) {
		//検索結果を取得
		List<Employee> list = empService.searchEmp(empName, empName_kana, companyName);
		
		model.addAttribute("employeeList", list);
		model.addAttribute("empName", empName);
		model.addAttribute("empName_kana", empName_kana);
		model.addAttribute("companyName", companyName);
		
		return "search";
	}
	
	//CSVインポートのGETメソッド
	@GetMapping("/import")
	public String getImport(Model model) {
		
		return "import";
	}
	
	//CSVインポートのPOSTメソッド
	@PostMapping(value = "/importFile", params = "upload_file")
	public String uploadFile(@RequestParam("file") MultipartFile uploadFile, Model model) {
		empService.importCsv(uploadFile);
		
		return "redirect:/";
	}
	
	//CSVエクスポートのPOSTメソッド
	@PostMapping(value = "/export_data.csv", produces = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE
		      + "; charset=UTF-8; Content-Disposition: attachment")
	@ResponseBody
	public Object csvExport(@ModelAttribute("csvForm") csv records) throws JsonProcessingException {
		//検索結果のhiddenタグの内容をリスト形式に出力する
		List<csvExport> csvList = empService.exportCsv(records); 
		
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(csvExport.class).withHeader();
		
		return mapper.writer(schema).writeValueAsString(csvList);
	}
	
}