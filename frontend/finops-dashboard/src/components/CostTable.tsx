import { useState } from 'react';
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TablePagination,
  Chip,
  IconButton,
  Tooltip,
  Box,
  Typography,
  TextField,
  InputAdornment,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import SearchIcon from '@mui/icons-material/Search';
import { CostRecord } from '../types';
import { costApi } from '../services/api';

interface CostTableProps {
  costs: CostRecord[];
  onRefresh: () => void;
}

function CostTable({ costs, onRefresh }: CostTableProps) {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState('');

  const handleChangePage = (_event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this cost record?')) {
      try {
        await costApi.deleteCost(id);
        onRefresh();
      } catch (error) {
        console.error('Error deleting cost record:', error);
        alert('Failed to delete cost record');
      }
    }
  };

  const filteredCosts = costs.filter((cost) =>
    Object.values(cost).some((value) =>
      value?.toString().toLowerCase().includes(searchTerm.toLowerCase())
    )
  );

  const formatCurrency = (amount: number, currency: string) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: currency,
      minimumFractionDigits: 2,
    }).format(amount);
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const getProviderColor = (provider: string) => {
    const colors: Record<string, string> = {
      AWS: '#FF9900',
      Azure: '#0078D4',
      GCP: '#4285F4',
    };
    return colors[provider] || '#757575';
  };

  const getEnvironmentColor = (environment: string) => {
    const colors: Record<string, "success" | "warning" | "error" | "info" | "default"> = {
      production: 'error',
      staging: 'warning',
      development: 'info',
    };
    return colors[environment.toLowerCase()] || 'default';
  };

  return (
    <Paper elevation={2}>
      <Box sx={{ p: 2 }}>
        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
          <Typography variant="h6" fontWeight="600">
            Cost Records ({filteredCosts.length})
          </Typography>
          <TextField
            size="small"
            placeholder="Search..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon />
                </InputAdornment>
              ),
            }}
            sx={{ width: 300 }}
          />
        </Box>

        <TableContainer>
          <Table>
            <TableHead>
              <TableRow sx={{ backgroundColor: '#f5f5f5' }}>
                <TableCell><strong>Provider</strong></TableCell>
                <TableCell><strong>Service</strong></TableCell>
                <TableCell><strong>Resource Name</strong></TableCell>
                <TableCell><strong>Cost</strong></TableCell>
                <TableCell><strong>Region</strong></TableCell>
                <TableCell><strong>Department</strong></TableCell>
                <TableCell><strong>Environment</strong></TableCell>
                <TableCell><strong>Usage Date</strong></TableCell>
                <TableCell align="center"><strong>Actions</strong></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredCosts
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((cost) => (
                  <TableRow
                    key={cost.id}
                    hover
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell>
                      <Chip
                        label={cost.cloudProvider}
                        size="small"
                        sx={{
                          backgroundColor: getProviderColor(cost.cloudProvider),
                          color: 'white',
                          fontWeight: 600,
                        }}
                      />
                    </TableCell>
                    <TableCell>{cost.serviceName}</TableCell>
                    <TableCell>
                      <Tooltip title={`ID: ${cost.resourceId}`}>
                        <span>{cost.resourceName}</span>
                      </Tooltip>
                    </TableCell>
                    <TableCell>
                      <Typography
                        variant="body2"
                        fontWeight="600"
                        color={cost.cost > 1000 ? 'error' : 'text.primary'}
                      >
                        {formatCurrency(cost.cost, cost.currency)}
                      </Typography>
                    </TableCell>
                    <TableCell>{cost.region}</TableCell>
                    <TableCell>{cost.department}</TableCell>
                    <TableCell>
                      <Chip
                        label={cost.environment}
                        size="small"
                        color={getEnvironmentColor(cost.environment)}
                      />
                    </TableCell>
                    <TableCell>{formatDate(cost.usageDate)}</TableCell>
                    <TableCell align="center">
                      <Tooltip title="Delete">
                        <IconButton
                          size="small"
                          color="error"
                          onClick={() => handleDelete(cost.id)}
                        >
                          <DeleteIcon />
                        </IconButton>
                      </Tooltip>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>

        <TablePagination
          rowsPerPageOptions={[5, 10, 25, 50]}
          component="div"
          count={filteredCosts.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Box>
    </Paper>
  );
}

export default CostTable;