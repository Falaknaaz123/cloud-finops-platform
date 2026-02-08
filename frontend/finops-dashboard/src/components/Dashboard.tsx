import { useState } from 'react';
import {
  Grid,
  Box,
  Tabs,
  Tab,
} from '@mui/material';
import SummaryCards from './SummaryCards';
import CostTable from './CostTable';
import CostCharts from './CostCharts';
import { CostRecord, CostSummary } from '../types';

interface DashboardProps {
  costs: CostRecord[];
  summary: CostSummary;
  onRefresh: () => void;
}

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`tabpanel-${index}`}
      aria-labelledby={`tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ pt: 3 }}>{children}</Box>}
    </div>
  );
}

function Dashboard({ costs, summary, onRefresh }: DashboardProps) {
  const [tabValue, setTabValue] = useState(0);

  const handleTabChange = (_event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
  };

  return (
    <Box>
      <SummaryCards summary={summary} />

      <Box sx={{ mt: 4 }}>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
          <Tabs value={tabValue} onChange={handleTabChange}>
            <Tab label="Overview & Charts" />
            <Tab label="Cost Records Table" />
          </Tabs>
        </Box>

        <TabPanel value={tabValue} index={0}>
          <CostCharts costs={costs} summary={summary} />
        </TabPanel>

        <TabPanel value={tabValue} index={1}>
          <CostTable costs={costs} onRefresh={onRefresh} />
        </TabPanel>
      </Box>
    </Box>
  );
}

export default Dashboard;