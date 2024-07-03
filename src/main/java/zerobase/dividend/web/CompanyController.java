package zerobase.dividend.web;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import zerobase.dividend.model.Company;
import zerobase.dividend.model.constants.CacheKey;
import zerobase.dividend.persist.entity.CompanyEntity;
import zerobase.dividend.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    private final CacheManager redisCacheManager;

    /* **************************************************************************************
     * GET /company/autocomplete
     * autocomplete :
     * ************************************************************************************** */
    @GetMapping("/autocomplete") // RequestMapping 안 쓴다면 /company/autocomplete로 입력
    public ResponseEntity<?> autocompleteCompany(@RequestParam(value="keyword") String keyword) {
        //var result = this.companyService.autocompletes(keyword); // trie 활용
        var result = this.companyService.getCompanyNamesByKeyword(keyword); // like 활용
        return ResponseEntity.ok(result);
    }

    /* **************************************************************************************
     * GET /company
     * searchCompany : 회사 리스트 조회
     * @param pageable
     * ************************************************************************************** */
    @GetMapping // RequestMapping 안 쓴다면 /company로 입력
    @PreAuthorize("hasRole('READ')")
    public ResponseEntity<?> searchCompany(final Pageable pageable) {
        Page<CompanyEntity> companies = this.companyService.getAllCompany(pageable);
        return ResponseEntity.ok(companies);
    }

    /* **************************************************************************************
     * Post /company
     * addCompany : 회사 및 배당금 정보 추가
     * @param requset
     * ************************************************************************************** */
    @PostMapping // RequestMapping 안 쓴다면 /company로 입력
    @PreAuthorize("hasRole('WRITE')")
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty(ticker)) {
            throw new RuntimeException("ticker is empty");
        }

        Company company = this.companyService.save(ticker);
        this.companyService.addAutocompleteKeyword(company.getName());
        return ResponseEntity.ok(company);
    }

    /* **************************************************************************************
     * Delete /company
     * deleteCompany : 회사 삭제
     * ************************************************************************************** */
    @DeleteMapping("/{ticker}") // RequestMapping 안 쓴다면 /company로 입력
    @PreAuthorize("hasRole('WRITE')")
    public ResponseEntity<?> deleteCompany(@PathVariable String ticker) {
        String companyName = this.companyService.deleteCompany(ticker);
        this.clearFinanceCache(companyName);
        return ResponseEntity.ok(companyName);
    }

    public void clearFinanceCache(String companyName) {
        this.redisCacheManager.getCache(CacheKey.KEY_FINANCE).evict(companyName);
    }
}
