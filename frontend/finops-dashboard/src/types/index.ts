export interface CostRecord {
  id: number;
  cloudProvider: string;
  serviceName: string;
  resourceId: string;
  resourceName: string;
  cost: number;
  currency: string;
  region: string;
  accountId: string;
  department: string;
  environment: string;
  usageDate: string;
  createdAt: string;
}

export interface CostSummary {
  totalCost: number;
  currency: string;
  totalRecords: number;
  costByProvider: Record<string, number>;
  costByService: Record<string, number>;
}