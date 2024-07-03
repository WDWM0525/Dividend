package zerobase.dividend.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.dividend.service.FinanceService;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    /* **************************************************************************************
     * GET /finance/dividend/{companyName}
     * serachFinance : 특정 회사의 배당금 조회
     * @param companyName
     * ************************************************************************************** */
    @GetMapping("/dividend/{companyName}")
    public ResponseEntity<?> serachFinance(@PathVariable("companyName") String companyName) {
        var result = this.financeService.getDividendByCompanyName(companyName);
        return ResponseEntity.ok(result);
    }
}
